//package com.get.hyphenbackenduser.global.interceptor;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//public class RequestInterceptor implements HandlerInterceptor {
//
//    private final String prefix;
//
//    public RequestInterceptor(String prefix) {
//        this.prefix = prefix;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestUri = request.getRequestURI();
//        if (requestUri.contains(this.prefix)) {
//            String customUri = requestUri.substring( this.prefix.length() - 1, requestUri.length() - 1);
//            if (customUri.equals("")) customUri = "/";
//            request.getRequestDispatcher(customUri).forward(request, response);
//        }
//        return true;
//    }
//}