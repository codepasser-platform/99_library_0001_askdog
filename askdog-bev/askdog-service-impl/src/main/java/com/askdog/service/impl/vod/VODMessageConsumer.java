package com.askdog.service.impl.vod;

import com.aliyun.vod.client.*;
import com.aliyun.vod.common.MediaWorkflowMessageType;
import com.askdog.model.data.StorageRecord;
import com.askdog.model.data.video.FailedInfo;
import com.askdog.model.data.video.Video;
import com.askdog.model.data.video.VideoItem;
import com.askdog.model.data.video.VideoSnapshot;
import com.askdog.service.impl.storage.StorageRecorder;
import com.askdog.service.impl.storage.aliyun.OssConfiguration;
import com.askdog.service.impl.storage.aliyun.StorageConfiguration;
import com.askdog.service.impl.storage.aliyun.description.OssResourceDescription;
import com.askdog.service.impl.storage.aliyun.description.OssVideoResourceDescription;
import com.askdog.web.common.async.AsyncCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;

import static com.askdog.model.data.inner.ResourceState.PERSISTENT;
import static com.askdog.model.data.inner.ResourceType.PRODUCT_VIDEO;
import static com.askdog.model.data.video.FailedInfo.FailedType.SNAPSHOT;
import static com.askdog.model.data.video.FailedInfo.FailedType.TRANSCODE;
import static com.askdog.model.data.video.VideoItem.Definition.parse;
import static java.lang.Long.valueOf;
import static java.util.Collections.singletonList;

//@Configuration
public class VODMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(VODMessageConsumer.class);

    private VODClient client;

    @Autowired private AsyncCaller asyncCaller;
    @Autowired private StorageRecorder storageRecorder;

    @StorageConfiguration(PRODUCT_VIDEO)
    @Autowired private OssConfiguration vodConfiguration;

    @PostConstruct
    public void start() {
        client = new DefaultVODClient(vodConfiguration.getAccessId(), vodConfiguration.getAccessKey());
        asyncCaller.asyncCall(this::process);
    }

    private void process() {

        if (logger.isDebugEnabled()) {
            logger.debug("Start New Thread Id: " + Thread.currentThread().getId());
        }

        try {
            List<MediaWorkflowMessage> messages = client.popMessages(
                    vodConfiguration.getMns().getUrl(),
                    vodConfiguration.getMns().getQueueName(),
                    30);

            if (logger.isDebugEnabled()) {
                logger.debug("Thread Id: " + Thread.currentThread().getId() + ", Message Size: " + messages.size());
            }

            messages.forEach(message -> {
                try {

                    if (logger.isDebugEnabled()) {
                        logger.debug("Thread Id: " + Thread.currentThread().getId()
                                + ", Message receiptHandle: " + message.receiptHandle()
                                + ", Message FileUrl: " + message.fileURL()
                                + ", Message Type: " + message.type());
                    }

                    if (message.type() == MediaWorkflowMessageType.Start) {
                        deleteQueueMessage(singletonList(message.receiptHandle()));
                        return;
                    }

                    Media media = client.getMedia(message.messageWorkflowName(), message.fileURL());

                    Video video = new Video();
                    parseResource(video, media);

                    WorkflowExecutionOutput workflowExecutionOutput = media.workflowExecutionOutputs().get(0);
                    workflowExecutionOutput.transcodeOutputs().forEach(parseTranscodeOutput(video));
                    workflowExecutionOutput.snapshotOutputs().forEach(parseSnapshotOutput(video));

                    StorageRecord resource = storageRecorder.getResource(parseResourceId(message.fileURL()));
                    resource.setDescription(new OssVideoResourceDescription((OssResourceDescription) resource.getDescription(), video));
                    resource.setResourceState(PERSISTENT);
                    storageRecorder.save(resource);
                    storageRecorder.refreshResourceCahce(resource.getResourceId());

                    deleteQueueMessage(singletonList(message.receiptHandle()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            logger.error("Cannot process message : " + e.getMessage(), e);

            try {
                Thread.sleep(5000);

            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        } finally {
            asyncCaller.asyncCall(this::process);
        }
    }

    private Long parseResourceId(String fileURL) {
        String filePath = fileURL.substring(fileURL.lastIndexOf("/") + 1);
        return valueOf(filePath.substring(0, filePath.lastIndexOf(".")));
    }

    private void parseResource(Video video, Media media) {
        try {
            VideoItem videoItem = new VideoItem();
            videoItem.setUrl(media.fileURL());
            videoItem.setWidth(media.mediaInfo().width());
            videoItem.setHeight(media.mediaInfo().height());
            videoItem.setDuration(media.mediaInfo().duration());
            videoItem.setFormat(media.mediaInfo().format());
            videoItem.setFileSize(media.mediaInfo().fileSize());
            videoItem.setBitRate(media.mediaInfo().bitrate());
            videoItem.setDefinition(parse(media.mediaInfo().width()));
            video.setSource(videoItem);

        } catch (Exception e) {
            video.getFailures().add(new FailedInfo(SNAPSHOT, "InvalidParameter.InvalidResource", e.getMessage()));
        }
    }

    private Consumer<TranscodeOutput> parseTranscodeOutput(Video video) {
        return transcodeOutput -> {

            if (!transcodeOutput.isSuccess()) {
                com.aliyun.vod.common.FailedInfo failedInfo = transcodeOutput.failedInfo();
                video.getFailures().add(new FailedInfo(TRANSCODE, failedInfo.getCode(), failedInfo.getMessage()));
                return;
            }

            VideoItem videoItem = new VideoItem();
            videoItem.setUrl(replaceWithUrlAlias(transcodeOutput.ossUrl()));
            videoItem.setWidth(transcodeOutput.mediaInfo().width());
            videoItem.setHeight(transcodeOutput.mediaInfo().height());
            videoItem.setDuration(transcodeOutput.mediaInfo().duration());
            videoItem.setFormat(transcodeOutput.mediaInfo().format());
            videoItem.setFileSize(transcodeOutput.mediaInfo().fileSize());
            videoItem.setBitRate(transcodeOutput.mediaInfo().bitrate());
            videoItem.setDefinition(parse(transcodeOutput.mediaInfo().width()));

            video.getTranscodeVideos().add(videoItem);
        };
    }

    private Consumer<SnapshotOutput> parseSnapshotOutput(Video video) {
        return snapshotOutput -> {

            if (!snapshotOutput.isSuccess()) {
                com.aliyun.vod.common.FailedInfo failedInfo = snapshotOutput.failedInfo();
                video.getFailures().add(new FailedInfo(SNAPSHOT, failedInfo.getCode(), failedInfo.getMessage()));
                return;
            }

            video.getSnapshots().add(parseVideoSnapshot(snapshotOutput));
        };
    }

    private VideoSnapshot parseVideoSnapshot(SnapshotOutput snapshotOutput) {
        VideoSnapshot videoSnapshot = new VideoSnapshot();
        videoSnapshot.setUrl(replaceWithUrlAlias(snapshotOutput.ossUrl()));
        return videoSnapshot;
    }

    private void deleteQueueMessage(List<String> receiptHandles) {
        try {
            client.deleteQueueMessages(
                    vodConfiguration.getMns().getUrl(),
                    vodConfiguration.getMns().getQueueName(),
                    receiptHandles);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String replaceWithUrlAlias(String ossUrl) {
        return ossUrl.replace(ossUrl.substring(7, ossUrl.indexOf("com") + 3), vodConfiguration.getUrlAlias());
    }
}
