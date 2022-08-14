package com.yama.crowd.service;

import com.yama.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * 权限模块服务层接口
 */
public interface AuthService {
    /**
     * 获取所有权限信息
     * @return
     */
    List<Auth> getAllAuth();

    /**
     * 获取当前角色所有的权限信息
     * @param roleId
     * @return
     */
    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    /**
     * 保存当前角色的权限信息
     * @param map
     */
    void saveRoleAuthRelathinship(Map<String, List<Integer>> map);

    /**
     * 根据用户id查询当前用户具有的权限
     * @param adminId
     * @return
     */
    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
