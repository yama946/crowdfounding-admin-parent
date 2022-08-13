package com.yama.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yama.crowd.entity.Role;
import com.yama.crowd.entity.RoleExample;
import com.yama.crowd.entity.RoleExample.*;
import com.yama.crowd.exceptions.NameInRoleAlreadyException;
import com.yama.crowd.mapper.RoleMapper;
import com.yama.crowd.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色模块业务层接口实现类
 */
@Slf4j
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 关键词查询分页角色
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @Override
    public PageInfo<Role> getRolePageInfoByKeyword(Integer pageNum, Integer pageSize, String keyword) {
        // 1.开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 2.执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        // 3.封装为 PageInfo 对象返回
        return new PageInfo<>(roleList);
    }

    /**
     * 保存角色功能
     * @param role
     */
    @Override
    public void save(Role role) {
        try {
            log.debug("开始保存角色");
            roleMapper.insert(role);
            log.debug("保存角色完成");
        }catch (Exception e){
            e.printStackTrace();
            throw new NameInRoleAlreadyException("角色名已被占用");
        }

    }

    /**
     * 更新角色功能
     *
     * 可能会出现更新的异常这里并没有考虑这种情况进行处理
     * @param role
     */
    @Override
    public void update(Role role) {
        try {
            roleMapper.updateByPrimaryKey(role);
            log.debug("角色更新完成");
        }catch (Exception e){
            e.printStackTrace();
            throw new NameInRoleAlreadyException("角色名已被占用");
        }

    }

    /**
     * 删除角色名功能
     * @param roleId
     */
    @Override
    public void remove(List<Integer> roleId) {
        RoleExample roleExample = new RoleExample();

        Criteria criteria = roleExample.createCriteria();

        criteria.andIdIn(roleId);

        roleMapper.deleteByExample(roleExample);
    }

    /**
     * 获取已分配的角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getAssignedRole(int adminId) {
        return roleMapper.seletAssignedRole(adminId);
    }

    /**
     * 获取未分配的角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getUnAssignedRole(int adminId) {
        return roleMapper.selectUnAssignedRole(adminId);

    }

    /**
     * 保存用户和角色信息
     * @param adminId
     * @param roleIdList
     */
    @Override
    public void saveAssignedAdminRole(int adminId, List<Integer> roleIdList) {
        //1.删除原有的用户和角色关系
        roleMapper.deleteAdminAssignRoleRelationship(adminId);
        //2.保存提交的用户和角色信息
        if (roleIdList!=null && roleIdList.size()>0){
            roleMapper.saveAdminAssignRoleRelationship(adminId,roleIdList);
        }

    }
}
