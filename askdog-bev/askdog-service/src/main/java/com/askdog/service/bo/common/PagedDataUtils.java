package com.askdog.service.bo.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

public class PagedDataUtils {

    public static <T, E> PagedData<E> rePage(Page<T> page, Pageable pageable, Function<T, E> convert) {
        List<E> result = newArrayList();
        if (page.hasContent()) {
            result.addAll(page.getContent().stream().map(convert).collect(toList()));
        }

        return new PagedData<>(result, pageable.getPageSize(), pageable.getPageNumber(), page.getTotalElements(), page.isLast());
    }

    public static <T, E> PagedData<E> rePage(PagedData<T> page, Function<T, E> convert) {
        List<E> result = newArrayList();
        if (page.getResult() != null) {
            result.addAll(page.getResult().stream().map(convert).collect(toList()));
        }

        return new PagedData<>(result, page.getSize(), page.getPage(), page.getTotal(), page.isLast());
    }
}
