package com.heng.blog_system.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpUtil {

    public static Object getSeesionBean(HttpServletRequest request,String key){
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }
}
