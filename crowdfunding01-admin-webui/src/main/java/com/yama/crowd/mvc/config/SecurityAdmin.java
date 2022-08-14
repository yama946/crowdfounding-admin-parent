package com.yama.crowd.mvc.config;

import com.yama.crowd.entity.Admin;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 为了拓展保存的数据我们继承User对象
 */
@Getter
public class SecurityAdmin extends User {

    //原始的admin对象
    private Admin originalAdmin;

    public SecurityAdmin(Admin admin,Collection<? extends GrantedAuthority> authorities) {
        super(admin.getLoginAcct(), admin.getUserPswd(), authorities);
        this.originalAdmin = admin;
        //将原始的admin中的密码擦除，再次之前以将密码传递到父类中用于验证，之后security会自动擦除
        this.originalAdmin.setUserPswd(null);
    }
}
