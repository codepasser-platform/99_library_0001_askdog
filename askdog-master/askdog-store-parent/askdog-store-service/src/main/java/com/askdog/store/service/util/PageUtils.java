package com.askdog.store.service.util;

import com.askdog.store.service.exception.ServiceException;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageUtils {

    public static <T, E> PageImpl<E> rePage(Page<T> page, Pageable pageable, ConvertFunction<T, E> convert) throws ServiceException {
        List<E> result = Lists.newArrayList();
        if (page.hasContent()) {
            for (T t : page.getContent())
                result.add(convert.apply(t));
        }
        return new PageImpl<>(result, pageable, page.getTotalElements());
    }
}
