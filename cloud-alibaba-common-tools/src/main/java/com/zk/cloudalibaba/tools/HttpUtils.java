package com.zk.cloudalibaba.tools;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

    public static String getTokenFromRequest(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                return cookie.getValue();
            }
        }
        return null;
    }
}
