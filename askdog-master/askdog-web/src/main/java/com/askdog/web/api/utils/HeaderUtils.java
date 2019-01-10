package com.askdog.web.api.utils;

import javax.servlet.http.HttpServletRequest;

import static com.google.common.base.Splitter.on;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.getFirst;

public class HeaderUtils {

    public static String getRequestRealIp(HttpServletRequest request) {
        // multi-tier
        String ip = request.getHeader("X-Forwarded-For");
        if (!isNullOrEmpty(ip)) {
            ip = getFirst(on(',').trimResults().omitEmptyStrings().split(ip), null);
        }
        if (!isResolved(ip)) {
            // single-tier
            ip = request.getHeader("X-Real-IP");
        }
        if (!isResolved(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static boolean isResolved(String ip) {
        return !(isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip));
    }
}
