package com.yama.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yama.crowd.constant.CrowdConstant;
import com.yama.crowd.entity.Admin;
import com.yama.crowd.entity.AdminExample;
import com.yama.crowd.entity.AdminExample.Criteria;
import com.yama.crowd.exceptions.LoginAcctAlreadyInUseException;
import com.yama.crowd.exceptions.LoginAcctAlreadyInUserUpdateException;
import com.yama.crowd.exceptions.LoginFaliedException;
import com.yama.crowd.mapper.AdminMapper;
import com.yama.crowd.service.AdminService;
import com.yama.crowd.util.CrowdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用户模块业务层接口实现类
 */
@Slf4j
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 登陆功能
     * 1、根据登陆账号和密码判断是否存在用户
     * @param loginAccount
     * @param loginPassword
     * @return
     */
    @Override
    public Admin getAdminByAccountAndPassword(String loginAccount, String loginPassword) {
        //根据账号查询admin对象
        //1.创建example对象
        AdminExample adminExample = new AdminExample();
        //2.创建构造条件查询的对象
        Criteria criteria = adminExample.createCriteria();
        //3.添加查询条件
        criteria.andLoginAcctEqualTo(loginAccount);
        //4.通过adminMapper进行查询
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        //判断获取的admin对象集合是否异常，异常抛出登陆异常交给异常处理器处理
        if (admins==null||admins.size()==0||admins.size()>1){
            throw new LoginFaliedException(CrowdConstant.ADMIN_LOGIN_MESSAGES);
        }
        //获取存在的登陆用户与登陆用户的密码
        Admin admin = admins.get(0);
        if (admin==null){
            throw new LoginFaliedException(CrowdConstant.ADMIN_LOGIN_MESSAGES);
        }
        String sourcePwd = admin.getUserPswd();
        //将用户登陆密码进行加密与数据库中保存的密码进行比较
        String targetPwd = CrowdUtil.md5(loginPassword);
        if (!Objects.equals(sourcePwd,targetPwd)){
            throw new LoginFaliedException(CrowdConstant.ADMIN_LOGIN_MESSAGES);
        }
        //比较成功返回用户
        return admin;
    }

    /**
     * 分页功能
     * 根据关键词查询，并分页操作
     * @param keyword   关键词
     * @param pageNum   页数
     * @param pageSize  记录数
     * @return
     */
    public PageInfo<Admin> getAdminPageInfo(String keyword,Integer pageNum,Integer pageSize){
        //1.开启分页功能
        //这里充分体现了pageHelper的非侵入式设计，原来要做的查询不需要进行改变，
        PageHelper.startPage(pageNum,pageSize);
        log.debug("开启pageHelper功能");

        //2.查询所有符合条件的数据
        List<Admin> admins = adminMapper.selectAdminByKeyword(keyword);
        log.debug("查询到数据为：{}",admins.size());

        //3.将符合条件的数据包装成pageInfo对象返回
        PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);

        return adminPageInfo;
    }

    /**
     * 删除用户功能
     * @param id
     */
    @Override
    public void remove(Integer id) {
        //查询用户
        adminMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增用户功能
     * @param admin
     */
    @Override
    public void saveAdmin(Admin admin) {
        // 针对登录密码进行加密
        String userPswd = admin.getUserPswd();
//        String target = CrowdUtil.md5(userPswd);
        String target = bCryptPasswordEncoder.encode(userPswd);
        admin.setUserPswd(target);
        // 执行保存，如果账户被占用会抛出异常
        // 表单的唯一性约束会导致插入失败
        try{
            //插入失败，sql的原生jdbc会报异常
            adminMapper.insert(admin);
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof DuplicateKeyException){
                //抛出我们的自定义异常
                log.debug("用户新增异常："+e.getClass().getName());
                throw new LoginAcctAlreadyInUseException("账户已被占用请，请重新添加");
            }else {
                log.debug("用户新增时系统异常，异常原因为"+e.getClass().getName());
                throw new RuntimeException("系统执行操作异常");
            }
        }
    }

    /**
     * 更新用户
     * @param admin
     */
    @Override
    public void updateAdmin(Admin admin) {
        try{
            //选择性更新用户
            adminMapper.updateByPrimaryKeySelective(admin);
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof DuplicateKeyException){
                //抛出我们的自定义异常
                log.debug("用户更新异常："+e.getClass().getName());
                throw new LoginAcctAlreadyInUserUpdateException("更新账户已被占用");
            }else {
                log.debug("用户更新时系统异常，异常原因为"+e.getClass().getName());
                throw new RuntimeException("系统执行操作异常");
            }
        }
    }

    /**
     * 根据id查询指定用户，进行表单回显数据
     * @param adminId
     * @return
     */
    @Override
    public Admin getAdminById(Integer adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        return admin;
    }

    /**
     * springsecurity中通过账号获取admin
     * @param loginAcct
     * @return
     */
    @Override
    public Admin getAdminByLoginAcct(String loginAcct) {
        AdminExample adminExample = new AdminExample();
        Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return admins.get(0);
    }
}
