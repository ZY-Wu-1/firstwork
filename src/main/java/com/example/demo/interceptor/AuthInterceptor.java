package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 * - 实现HandlerInterceptor接口
 * - preHandle: 请求处理前调用，返回true放行，false拦截
 * - 检查请求头中是否携带token，模拟权限验证
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    /**
     * 请求预处理
     * - 检查请求头 Authorization 是否存在
     * - 不存在则返回401，拒绝访问
     * - 存在则放行到Controller
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        // 简单校验：token不能为空（实际项目中应校验token有效性）
        if (token == null || token.trim().isEmpty()) {
            log.warn("[拦截器] 请求被拒绝: {}, 缺少Token", request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未授权，请添加Authorization请求头\"}");
            return false;
        }

        log.info("[拦截器] 请求放行: {} {}, Token: {}", request.getMethod(), request.getRequestURI(), token);
        return true;
    }
}