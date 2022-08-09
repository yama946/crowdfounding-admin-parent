package com.yama.crowd.test;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//springmvc异常处理器的xml配置方式之一

/**
 * HandlerExceptionResolve和注解@ExceptionHandler的区别
 * 自定义的异常处理类实现HandlerExceptionResolve接口，在上面加上@Component注解以后，
 * 我们的springmvc就知道这是我们springmvc的异常处理类，发生异常会自动调用这个类中的我们重写的方法。
 *
 * 要注意：这种实现springmvc提供好的异常处理的接口的方式，这个类的工作时机是在我们的控制器方法接收完参数以后才会工作的，
 * 说白了就是这个异常处理加载时机比较晚，如果我们在接收参数的期间发生了参数的类型转换异常，那么这个异常的处理是不会执行的。
 *
 * 使用@ExceptionHandler注解声明的异常处理方式，发生任何异常都可以被其捕获，
 * 前提是我们自定义了这种异常并且配置到这个注解里面，或者不进行配置直接让其捕获所有异常。
 * （这种方式在springmvc笔记中有写），另外，这种配置的方式的加载时机可以理解为在中央调度器DispatcherServlet加载以后就会进行加载，
 * 实际上比Controller的加载时机更早。
 */
public class CustomExceptionResolvers implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        return null;
    }
}
