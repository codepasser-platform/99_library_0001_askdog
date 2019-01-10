package com.askdog.service.impl.snapshot;

import com.askdog.model.data.snapshot.CommentSnapshot;
import com.askdog.model.repository.mongo.snapshot.CommentSnapshotRepository;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.snapshot.CommentSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentSnapshotServiceImpl implements CommentSnapshotService {

    @Autowired private CommentSnapshotRepository commentSnapshotRepository;

    @Override public CommentSnapshot save(CommentDetail commentDetail) {
        return commentSnapshotRepository.save(new CommentSnapshot()
                .setCommentId(commentDetail.getUuid())
                .setCommenterId(commentDetail.getCommenter().getUuid())
                .setOwnerId(commentDetail.getOwnerId())
                .setContent(commentDetail.getContent())
                .setCreationTime(commentDetail.getCreationTime())
                .setSnapshotTime(new Date()));
    }
}
