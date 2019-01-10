package com.askdog.model.repository.mongo.snapshot;

import com.askdog.model.data.snapshot.CommentSnapshot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommentSnapshotRepository extends MongoRepository<CommentSnapshot, String> {
    Optional<CommentSnapshot> findByCommentId(String commentId);
}
