package com.yama.crowd.service;

import com.github.pagehelper.PageInfo;
import com.yama.crowd.entity.Admin;

/**
 * 用户模块业务层接口
 */
public interface AdminService {
    /**
     * 根据登陆账号和密码判断是否存在用户
     * @param loginAccount
     * @param loginPassword
     * @return
     */
    Admin getAdminByAccountAndPassword(String loginAccount,String loginPassword);


    /**
     * 根据关键词查询，并分页操作
     * @param keyword   关键词
     * @param pageNum   页数
     * @param pageSize  记录数
     * @return
     */
    PageInfo<Admin> getAdminPageInfo(String keyword,Integer pageNum,Integer pageSize);

    /**
     * 删除用户功能
     * @param id
     */
    void remove(Integer id);

    /**
     * 新增用户功能
     * @param admin
     */
    void saveAdmin(Admin admin);

    /**
     * 更新用户
     * @param admin
     */
    void updateAdmin(Admin admin);

    /**
     * 根据id查询指定用户，进行表单回显数据
     * @param adminId
     * @return
     */
    Admin getAdminById(Integer adminId);
}
