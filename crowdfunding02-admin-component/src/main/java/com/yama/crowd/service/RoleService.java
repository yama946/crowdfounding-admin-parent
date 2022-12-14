package com.yama.crowd.service;

import com.github.pagehelper.PageInfo;
import com.yama.crowd.entity.Role;

import java.util.List;

/**
 * 角色模块业务层接口
 */
public interface RoleService {
    /**
     * 关键词查询分页角色
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    PageInfo<Role> getRolePageInfoByKeyword(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 保存角色功能
     * @param role
     */
    void save(Role role);

    /**
     * 更新角色功能
     * @param role
     */
    void update(Role role);

    /**
     * 删除角色名功能
     * @param roleId
     */
    void remove(List<Integer> roleId);

    /**
     * 获取已分配的角色信息
     * @param adminId
     * @return
     */
    List<Role> getAssignedRole(int adminId);

    /**
     * 查询未分配的角色信息
     * @param adminId
     * @return
     */
    List<Role> getUnAssignedRole(int adminId);

    /**
     * 保存用户和角色色信息
     * @param adminId
     * @param roleIdList
     */
    void saveAssignedAdminRole(int adminId, List<Integer> roleIdList);
}
