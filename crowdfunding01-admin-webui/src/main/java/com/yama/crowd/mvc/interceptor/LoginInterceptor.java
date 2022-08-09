package com.yama.crowd.mvc.interceptor;

import com.yama.crowd.entity.Admin;
import com.yama.crowd.exceptions.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    //执行controller之前执行

    /**
     * 通过获取session，判断用户是否已经登陆
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session
        HttpSession session = request.getSession();
        //尝试获取admin
        Admin admin = (Admin)session.getAttribute("admin");
        //判断
        if (admin==null){
            throw new AccessForbiddenException("用户未登录,不可访问页面");
        }
        return true;
    }
}
