package com.askdog.web.api.vo.common;

import com.askdog.web.api.vo.profile.incentive.GroupDate;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Function;

public class GroupDataResultHelper {

    @SuppressWarnings("unchecked")
    public static <E> List<GroupDateResult<E>> group(List<E> data, Function<E, GroupDate> dateGroupTFunction) {
        if (data == null) {
            return null;
        }

        List<GroupDateResult<E>> result = Lists.newArrayList();

        data.forEach(e -> {
            GroupDate groupDate = dateGroupTFunction.apply(e);

            for (GroupDateResult<E> groupDateResult : result) {
                if (groupDateResult.getGroupDate().equals(groupDate)) {
                    groupDateResult.getGroupData().add(e);
                    return;
                }
            }

            result.add(new GroupDateResult<E>().setGroupDate(groupDate).setGroupData(Lists.newArrayList(e)));

        });

        return result;
    }
}
