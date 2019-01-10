package com.askdog.web.api.vo.common;

import com.askdog.web.api.vo.profile.incentive.GroupDate;

import java.util.List;

public class GroupDateResult<E> {

    private GroupDate groupDate;
    private List<E> groupData;

    public GroupDate getGroupDate() {
        return groupDate;
    }

    public GroupDateResult<E> setGroupDate(GroupDate groupDate) {
        this.groupDate = groupDate;
        return this;
    }

    public List<E> getGroupData() {
        return groupData;
    }

    public GroupDateResult<E> setGroupData(List<E> groupData) {
        this.groupData = groupData;
        return this;
    }
}
