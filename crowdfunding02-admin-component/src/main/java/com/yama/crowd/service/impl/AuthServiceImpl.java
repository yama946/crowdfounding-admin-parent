package com.yama.crowd.service.impl;

import com.yama.crowd.entity.Auth;
import com.yama.crowd.entity.AuthExample;
import com.yama.crowd.mapper.AuthMapper;
import com.yama.crowd.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 权限模块服务层接口实现类
 */
@Slf4j
@Service("authService")
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    /**
     * 获取所有权限信息
     * @return
     */
    @Override
    public List<Auth> getAllAuth() {
        AuthExample authExample = new AuthExample();
        List<Auth> auths = authMapper.selectByExample(authExample);
        return auths;
    }

    /**
     * 获取当角色所有权限信息
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectRoleAllauth(roleId);
    }

    /**
     * 保存当前角色的权限信息
     * @param map
     */
    @Override
    public void saveRoleAuthRelathinship(Map<String, List<Integer>> map) {
        //1.获取角色id，并删除当前角色的所有权限
        List<Integer> role_id = map.get("roleId");
        int roleId = role_id.get(0);
        authMapper.deleteRoleAllAuth(roleId);
        //2.获取当前角色的所有权限，并插入表中
        List<Integer> authIdArray = map.get("authIdArray");
        if (authIdArray!=null&& authIdArray.size()>0){
            authMapper.saveRoleAssignAuthInformation(roleId,authIdArray);
        }
    }
}
