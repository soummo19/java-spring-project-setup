package com.soumyadeep.demo.utils;

import jakarta.servlet.http.HttpServletRequest;

public class DemoUtils {

    public static String getSourceIp(HttpServletRequest request){
        String xff = request.getHeader(Constants.HDR_XFF);
        String clientIp = (xff != null && !xff.isBlank()) ? xff.split(",")[0].trim() : request.getRemoteAddr();
        return clientIp;
    }
}
