package com.yama.crowd.test;

import com.yama.crowd.entity.Admin;
import com.yama.crowd.mapper.AdminMapper;
import com.yama.crowd.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * spring整合mybatis测试
 */
@RunWith(SpringJUnit4ClassRunner.class)//用来替换junit中启动方法，此类可以获取容器
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})//用来获取配置地址，创建容器
public class SpringMybatisTest {
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 创建用户
     */
    @Test
    public void createAdmin(){
        final String PWD="123456";
        String password = CrowdUtil.md5(PWD);
        for (int i=1;i<40;i++){
            String loginAcct=i+"yanmu";
            String userName="yanruyi"+i;
            String email=i+"message";
            Admin admin = new Admin(null,loginAcct , password, userName, email,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
            adminMapper.insert(admin);
        }

    }
}
