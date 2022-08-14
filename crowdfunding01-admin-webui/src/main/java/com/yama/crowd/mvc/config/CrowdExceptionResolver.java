package com.yama.crowd.mvc.config;

import com.google.gson.Gson;
import com.yama.crowd.entity.Admin;
import com.yama.crowd.exceptions.*;
import com.yama.crowd.util.CrowdUtil;
import com.yama.crowd.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 注解配置异常控制器
 *springsecurity中权限访问异常中，如果是在security配置类中配置权限导致异常，将会走配置类中的异常
 * 如果配置类中没有配置相关异常处理机制，将会报错默认的错误页面
 *
 * 注解@PreAuthority作用在控制器方法上的权限控制，将会走springmvc中配置的注解异常处理机制
 */
@Slf4j
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = NameInRoleAlreadyException.class)
    public ModelAndView resolveNameInRoleAlreadyException(
            NameInRoleAlreadyException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        // 只是指定当前异常对应的页面即可
        String viewName = "page/role-page";
        return common(exception, request, response, viewName);
    }


    @ExceptionHandler(value = LoginAcctAlreadyInUserUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(
            LoginAcctAlreadyInUserUpdateException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        // 只是指定当前异常对应的页面即可
        String viewName = "page/admin-update";
        return common(exception, request, response, viewName);
    }

    @ExceptionHandler(value =LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(
            LoginAcctAlreadyInUseException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        // 只是指定当前异常对应的页面即可
        String viewName = "page/admin-add";
        return common(exception, request, response, viewName);
    }


    @ExceptionHandler(LoginFaliedException.class)
    public ModelAndView loginFailedExceptionResolver(
            Exception exception, HttpServletRequest request, HttpServletResponse response
    ){
        String viewName = "exception/system-err";
        return common(exception,request,response,viewName);
    }

    @ExceptionHandler(AccessForbiddenException.class)
    public ModelAndView AccessForbiddenExceptionResolver(
            Exception exception, HttpServletRequest request, HttpServletResponse response
    ){
        String viewName = "exception/system-err";
        return common(exception,request,response,viewName);
    }


    @ExceptionHandler({Exception.class})
    public ModelAndView commonExceptionResolver(
            Exception exception, HttpServletRequest request, HttpServletResponse response
    ){
        String viewName = "exception/system-err";
        return common(exception,request,response,viewName);
    }

    /**
     *
     * @param exception //springmvc会封装异常对象传递过来
     * @param request   //spring mvc会封装请求对象传递过来
     * @param response
     * @return
     */
    public ModelAndView common(
            Exception exception, HttpServletRequest request, HttpServletResponse response,String viewName) {
        //第一步；判断请求类型
        boolean requestType = CrowdUtil.judgeRequestType(request);
        if (requestType){
            //当前请求时ajax请求，返回json对象。
            //将异常信息封装到result对象中返回
            String exceptionMessage = exception.getMessage();
            ResultUtil result = ResultUtil.fail(exceptionMessage);
            //转化为json对象进行返回
            Gson gson = new Gson();
            String resultJson = gson.toJson(result);
            try {
                log.debug("ajax异常，返回异常信息");
                exception.printStackTrace();
                response.getWriter().write(resultJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        //当前是普通请求，需要返回一个页面响应
        ModelAndView modelAndView = new ModelAndView();
        //也可以将异常信息放置到model中在异常页面中获取显示
        log.debug("添加到模块中的异常为:{}",exception);
        /**
         * modelAndView.addObject(exception);
         * 如果不指定参数行，对象的类型作为参数名进行添加到模块中
         */
//        modelAndView.addObject(exception);
        modelAndView.addObject("exception",exception);
        HttpSession session = request.getSession();
        Admin adminUpdate = (Admin)session.getAttribute("adminUpdate");
        modelAndView.addObject("admin",adminUpdate);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
