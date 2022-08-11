package com.yama.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.yama.crowd.entity.Role;
import com.yama.crowd.service.RoleService;
import com.yama.crowd.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制层
 */
@Controller
@ResponseBody
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    /**
     * 关键词查询并分页功能
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @RequestMapping("/role/get/page/info.json")
    public ResultUtil<PageInfo<Role>> getPageInfo(
            //当前页
            @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
            //记录数
            @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
            //关键词
            @RequestParam(value="keyword", defaultValue="") String keyword){

        PageInfo<Role> rolePageInfo = roleService.getRolePageInfoByKeyword(pageNum, pageSize, keyword);

        return ResultUtil.ok(rolePageInfo);
    }

    /**
     * 保存角色功能
     * @param name
     * @return
     */
    @RequestMapping("/role/save.json")
    public ResultUtil<String> saveRole(
            @RequestParam("name")String name
    ){
        Role role = new Role(null, name);
        roleService.save(role);
        return ResultUtil.ok(null);
    }

    /**
     * 角色更新功能
     * @param role
     * @return
     */
    @RequestMapping("/role/update.json")
    public ResultUtil<String> updateRole(Role role){
        roleService.update(role);
        return ResultUtil.ok(null);
    }

    /**
     * 删除角色功能
     * @param roleId
     * @return
     */
    @RequestMapping("/role/remove/by/id/array.json")
    public ResultUtil<String> removeRole(@RequestBody List<Integer> roleId){
        roleService.remove(roleId);
        return ResultUtil.ok(null);
    }
}
