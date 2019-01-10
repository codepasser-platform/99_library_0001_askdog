package com.askdog.model.data.snapshot;

import com.askdog.model.data.Base;

import java.util.Date;

public class Snapshot<T> extends Base {

    private Date snapshotTime;

    public Date getSnapshotTime() {
        return snapshotTime;
    }

    @SuppressWarnings("unchecked") public T setSnapshotTime(Date snapshotTime) {
        this.snapshotTime = snapshotTime;
        return (T) this;
    }
}
