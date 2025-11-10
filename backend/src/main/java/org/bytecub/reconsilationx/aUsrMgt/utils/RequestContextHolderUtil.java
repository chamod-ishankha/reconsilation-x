package org.bytecub.reconsilationx.aUsrMgt.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestContextHolderUtil {
    public static String getClientIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader("X-Forwarded-For") != null ?
                request.getHeader("X-Forwarded-For").split(",")[0].trim() :
                request.getRemoteAddr();
    }

    public static String getUserAgent() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader("User-Agent");
    }
}
