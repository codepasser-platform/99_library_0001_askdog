package com.askdog.service.snapshot;

import com.askdog.model.data.snapshot.CommentSnapshot;
import com.askdog.service.bo.CommentDetail;

public interface CommentSnapshotService {

    CommentSnapshot save(CommentDetail commentDetail);

}
