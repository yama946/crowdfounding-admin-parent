package com.yama.crowd.mvc.handler;

import com.yama.crowd.entity.Auth;
import com.yama.crowd.entity.Role;
import com.yama.crowd.service.AdminService;
import com.yama.crowd.service.AuthService;
import com.yama.crowd.service.RoleService;
import com.yama.crowd.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 分配模块控制层---用户分配角色、角色分配权限
 */
@Controller
@Slf4j
public class AssignHandler {
    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    /**
     * 转发到分配角色页面
     * @param adminId
     * @return
     */
    @RequestMapping("assign/admin/to/role/page.html")
    public String toAssignRole(
            //要分配角色的用户id
            @RequestParam("adminId") int adminId,
            //获取到角色信息保存进模块
            ModelMap model
    ){
        //1.已分配的角色信息
      List<Role> assignRoleList = roleService.getAssignedRole(adminId);
      //2.未分配的角色信息
      List<Role> unassignRoleList = roleService.getUnAssignedRole(adminId);

      //3.将分配信息存放到模块中
        model.addAttribute("assignRoleList",assignRoleList);
        model.addAttribute("unassignRoleList",unassignRoleList);

        //返回分配角色页面
        return "page/assign-role";
    }

    /**
     * 保存被分配的角色
     * @return
     */
    @RequestMapping("assign/admin/do/role/assign.html")
    public String saveAssignedRole(
            @RequestParam("pageNum")int pageNum,
            @RequestParam("keyword")String keyword,
            @RequestParam("adminId")int adminId,
            //提供要保存的角色信息
            @RequestParam(value = "roleIdList",required = false)List<Integer> roleIdList
    ){
        log.debug("当前角色列表：{}",roleIdList);

        //将新的角色信息进行保存
        roleService.saveAssignedAdminRole(adminId,roleIdList);

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    /**
     * 获取所有权限信息进行模态框显示
     * @return
     */
    @ResponseBody
    @RequestMapping("assgin/get/all/auth.json")
    public ResultUtil<List<Auth>> getAllAuthInformation(){
        List<Auth> auths = authService.getAllAuth();
        return ResultUtil.ok(auths);
    }

    /**
     * 获取当前角色所拥有的权限
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultUtil<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId) {
        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultUtil.ok(authIdList);
    }

    /**
     * 保存当前角色的权限
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultUtil<String> saveRoleAuthRelathinship(
            @RequestBody Map<String, List<Integer>> map) {
        authService.saveRoleAuthRelathinship(map);
        return ResultUtil.ok(null);
    }

}
