package com.askdog.store.web.api.vo.common;

import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.service.util.ConvertFunction;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageResultHelper {

    public static <T, E> PageResult<E> rePage(Page<T> page, ConvertFunction<T, E> function) throws ServiceException {
        List<E> result = Lists.newArrayList();
        if (page.getNumberOfElements() > 0) {
            for (T t : page) {
                result.add(function.apply(t));
            }
        }
        return new PageResult<>(result, page.getTotalElements(), page.isLast());
    }
}
