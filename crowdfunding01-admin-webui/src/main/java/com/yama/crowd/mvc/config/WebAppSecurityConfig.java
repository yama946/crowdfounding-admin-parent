package com.yama.crowd.mvc.config;

import com.yama.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
//开启springsecurity安全防护功能
@EnableWebSecurity
//启用全局方法权限控制功能，并且设置 prePostEnabled = true。保证@PreAuthority、@PostAuthority、@PreFilter、@PostFilter 生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService crowdUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 此处配置存放到springioc子容器中，service层父容器使用无法获取
     * @param auth
     * @throws Exception
     */
    /*@Bean
    public BCryptPasswordEncoder getbCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }*/


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //内存登陆验证的方式
        //对于5.xx版本要求配置完整的用户设置是：用户、密码、角色或者权限，否则sevlet启动异常
        /*auth
                .inMemoryAuthentication()
                .passwordEncoder(bCryptPasswordEncoder)
                .withUser("yanmu123").roles("admin")
                .password(bCryptPasswordEncoder.encode("123456"));*/
        //使用数据库进行获取用户名、密码、角色和权限信息
        auth
                .userDetailsService(crowdUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);

    }

    /**
     * springsecurity中的路径基本都是通过重定向的，所以需要添加”/“
     * @param security
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                //释放登陆请求的路径,其中路径上的“/”是必要的
                .antMatchers("/admin/to/login/index.html")
                .permitAll()
                //释放静态资源
                .antMatchers("/bootstrap/**","/crowd/**","/css/**","/fonts/**","/img/**",
                        "/jquery/**","/layer/**","/pagination/**","/script/**","/ztree/**")
                .permitAll()
                .antMatchers("/admin/remove/{id}/{pageNum}/{keyword}.html","/admin/get/page.html")
                .hasAnyAuthority("user:delete","user:get")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/admin/to/login/index.html")
                .loginProcessingUrl("/security/admin/do/login.html")
                .defaultSuccessUrl("/admin/to/main.html")
                .usernameParameter("loginAccount")
                .passwordParameter("loginPassword")
                .and()
                .logout()
                .logoutUrl("/security/admin/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/index.html")
                .and()
                .exceptionHandling()                        //指定异常处理器
                .accessDeniedPage("/to/no/auth/page.html")     //访问被拒绝时，要前往的页面
                //或者使用下面自定义的方式进行处理权限验证异常
                /*.accessDeniedHandler(new AccessDeniedHandler(){
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response,
                                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        request.setAttribute("exception", new
                                Exception("未授权无法登陆"));
                        request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request,
                                response);
                    }
                })*/
                ;
    }
}
