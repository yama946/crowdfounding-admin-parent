package com.yama.crowd.mvc.config;

import com.google.gson.Gson;
import com.yama.crowd.entity.Admin;
import com.yama.crowd.exceptions.AccessForbiddenException;
import com.yama.crowd.exceptions.LoginAcctAlreadyInUseException;
import com.yama.crowd.exceptions.LoginAcctAlreadyInUserUpdateException;
import com.yama.crowd.exceptions.LoginFaliedException;
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
 */
@Slf4j
@ControllerAdvice
public class CrowdExceptionResolver {


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