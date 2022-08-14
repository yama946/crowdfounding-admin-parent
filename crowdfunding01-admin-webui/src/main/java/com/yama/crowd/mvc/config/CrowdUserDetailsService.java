package com.yama.crowd.mvc.config;

import com.yama.crowd.entity.Admin;
import com.yama.crowd.entity.Role;
import com.yama.crowd.service.AdminService;
import com.yama.crowd.service.AuthService;
import com.yama.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * springsecurity配置类所需要的组件
 */
@Component("crowdUserDetailsService")
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    /**
     * 通过数据库获取用户名、密码、角色和权限
     * @param loginAcct
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {
        //1.通过账户进行查询admin对象
        Admin admin = adminService.getAdminByLoginAcct(loginAcct);
        //2.获取当前admin的id，进行获取当前id对应角色
        Integer adminId = admin.getId();
        List<Role> assignedRole = roleService.getAssignedRole(adminId);
        //3.根据角色查询到对应的权限名
        List<String> authNameByAdminId = authService.getAssignedAuthNameByAdminId(adminId);
        //4.将角色信息存放到GrantedAuthority集合中
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Role role:assignedRole){
            String roleName = "ROLE_"+role.getName();
            grantedAuthorityList.add(new SimpleGrantedAuthority(roleName));
        }
        for (String name : authNameByAdminId){
            grantedAuthorityList.add(new SimpleGrantedAuthority(name));
        }
        //5.将用户名和密码封装到User对象中，但是我们还需要更多的admin信息，所以我们进行继承User对象
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, grantedAuthorityList);
        return securityAdmin;
    }
}
