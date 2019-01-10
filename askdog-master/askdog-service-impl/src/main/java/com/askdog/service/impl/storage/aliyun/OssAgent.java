package com.askdog.service.impl.storage.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.askdog.model.data.StorageRecord;
import com.askdog.model.data.inner.ResourceType;
import com.askdog.service.exception.ConflictException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.storage.StorageRecorder;
import com.askdog.service.storage.AccessToken;
import com.askdog.service.storage.Provider;
import com.askdog.service.storage.StorageAgent;
import com.askdog.service.storage.StorageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.aliyun.oss.common.utils.BinaryUtil.toBase64String;
import static com.aliyun.oss.model.PolicyConditions.COND_CONTENT_LENGTH_RANGE;
import static com.askdog.common.utils.Json.writeValueAsString;
import static com.askdog.common.utils.Variables.variables;
import static com.askdog.model.data.inner.ResourceType.*;
import static com.askdog.model.data.inner.StorageProvider.ALI_OSS;
import static com.askdog.model.entity.StorageLink.Status.PERSISTENT;
import static com.google.common.collect.ImmutableMap.of;
import static com.google.common.io.Files.getFileExtension;
import static java.util.UUID.randomUUID;
import static org.elasticsearch.common.Strings.toUTF8Bytes;

@org.springframework.context.annotation.Configuration
@Component
@Provider(ALI_OSS)
public class OssAgent implements StorageAgent {

    private final Map<ResourceType, OSSClient> clients = new HashMap<>();
    private Map<ResourceType, OssConfiguration> configurations;

    @Autowired
    private StorageRecorder storageRecorder;

    @Autowired
    @Configuration(ANSWER_PICTURE)
    private OssConfiguration answerPictureConfiguration;

    @Autowired
    @Configuration(USER_AVATAR)
    private OssConfiguration userAvatarPictureConfiguration;

    @Autowired
    @Configuration(QUESTION_QR_CODE)
    private OssConfiguration questionQRConfiguration;

    @PostConstruct
    private void init() {
        configurations = of(
                ANSWER_PICTURE, answerPictureConfiguration,
                USER_AVATAR, userAvatarPictureConfiguration,
                QUESTION_QR_CODE, questionQRConfiguration
        );
    }

    @Nonnull
    @Override
    public AccessToken generateAccessToken(@Nonnull ResourceType type, @Nonnull String fileName) throws ServiceException {
        OssConfiguration configuration = getOssConfiguration(type);
        OssConfiguration.Policy policy = configuration.getPolicy();
        String baseDir = policy.getBaseDir();

        OssResourceDescription description = new OssResourceDescription();
        description.setBucket(configuration.getBucket());
        description.setFileName(fileName);
        description.setPersistenceName(baseDir + "/" + randomUUID().toString() + '.' + getFileExtension(fileName));
        StorageRecord record = storageRecorder.newRecord(ALI_OSS, type, description);

        Long maxSize = Long.valueOf(policy.getMaxSize());
        PolicyConditions conditions = new PolicyConditions();
        conditions.addConditionItem(COND_CONTENT_LENGTH_RANGE, 0, maxSize * 1024);
        conditions.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, description.getPersistenceName());

        Long expireTime = Long.valueOf(policy.getExpireTime());
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        String postPolicy = getOSSClient(type).generatePostPolicy(new Date(expireEndTime), conditions);

        OssAccessToken accessToken = new OssAccessToken();
        accessToken.setAccessId(configuration.getAccessId());
        accessToken.setPolicy(toBase64String(toUTF8Bytes(postPolicy)));
        accessToken.setSignature(getOSSClient(type).calculatePostSignature(postPolicy));
        accessToken.setPersistenceName(description.getPersistenceName());
        accessToken.setHost(configuration.getHost());
        accessToken.setExpire(expireEndTime);

        OssConfiguration.Callback callback = createResourceRecord(record, configuration);
        accessToken.setCallback(toBase64String(toUTF8Bytes(writeValueAsString(callback))));
        return accessToken;
    }

    @Nonnull
    @Override
    public StorageResource persistLink(@Nonnull String linkId) throws ServiceException {
        StorageRecord record = storageRecorder.updateLinkStatus(linkId, PERSISTENT);
        return new StorageResource(record.getLinkId(), getPictureUrl(record));
    }

    @Nonnull
    @Override
    public StorageResource find(@Nonnull String linkId) throws ServiceException {
        StorageRecord record = storageRecorder.findStorageRecord(linkId);
        return new StorageResource(record.getLinkId(), getPictureUrl(record));
    }

    @Nonnull
    @Override
    public StorageRecord save(@Nonnull ResourceType type, @Nonnull String fileName, @Nonnull InputStream stream) throws ServiceException {
        StorageRecord storageRecord = generateStorageRecord(type, fileName);
        getOSSClient(type).putObject(configurations.get(type).getBucket(), ((OssResourceDescription) storageRecord.getDescription()).getPersistenceName(), stream);
        persistLink(storageRecord.getLinkId());
        return storageRecord;
    }

    @Nonnull
    @Override
    public String resourceURL(@Nonnull ResourceType type, @Nonnull String fileName) throws ServiceException {
        OssConfiguration configuration = getOssConfiguration(type);
        OssConfiguration.Policy policy = configuration.getPolicy();
        String baseDir = policy.getBaseDir();
        return "http://" + configuration.getBucket() + "." + configuration.getEndpoint() + "/" + baseDir + "/" + fileName;
    }

    @Bean
    @Configuration(ANSWER_PICTURE)
    @ConfigurationProperties(prefix = "askdog.storage.picture.answer")
    public OssConfiguration answerPictureStorageConfig() {
        return new OssConfiguration();
    }

    @Bean
    @Configuration(USER_AVATAR)
    @ConfigurationProperties(prefix = "askdog.storage.picture.avatar")
    public OssConfiguration avatarStorageConfig() {
        return new OssConfiguration();
    }

    @Bean
    @Configuration(QUESTION_QR_CODE)
    @ConfigurationProperties(prefix = "askdog.storage.picture.question")
    public OssConfiguration questionQRConfig() {
        return new OssConfiguration();
    }

    private OssConfiguration.Callback createResourceRecord(StorageRecord record, OssConfiguration configuration) throws ConflictException {
        OssConfiguration.Callback callback = configuration.getCallback().clone();
        callback.setBody(variables(of("linkId", record.getLinkId())).replace(callback.getBody()));
        return callback;
    }

    private OSSClient getOSSClient(ResourceType type) {
        // not need synchronize it
        OSSClient client = clients.get(type);
        if (client == null) {
            OssConfiguration configuration = getOssConfiguration(type);
            client = new OSSClient(configuration.getEndpoint(), configuration.getAccessId(), configuration.getAccessKey());
            clients.put(type, client);
        }

        return client;
    }

    private OssConfiguration getOssConfiguration(ResourceType type) {
        return configurations.get(type);
    }

    private String getPictureUrl(StorageRecord record) {
        OssResourceDescription description = (OssResourceDescription) record.getDescription();
        OssConfiguration configuration = getOssConfiguration(record.getResourceType());
        return "http://" + description.getBucket() + "." + configuration.getEndpoint() + "/" + description.getPersistenceName();
    }

    private StorageRecord generateStorageRecord(@Nonnull ResourceType type, @Nonnull String fileName) throws ServiceException {
        OssConfiguration configuration = getOssConfiguration(type);
        OssConfiguration.Policy policy = configuration.getPolicy();
        String baseDir = policy.getBaseDir();

        OssResourceDescription description = new OssResourceDescription();
        description.setBucket(configuration.getBucket());
        description.setFileName(fileName);
        description.setPersistenceName(baseDir + "/" + fileName);
        return storageRecorder.newRecord(ALI_OSS, type, description);
    }

}
