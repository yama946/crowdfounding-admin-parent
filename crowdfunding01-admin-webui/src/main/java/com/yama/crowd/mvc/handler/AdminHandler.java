package com.yama.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.yama.crowd.entity.Admin;
import com.yama.crowd.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户处理器
 */
@Slf4j
@Controller
@CrossOrigin
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    /**
     * 登陆功能
     * 用户登陆处理
     * @return
     */
    @RequestMapping("admin/do/login.html")
    public String doLogin(@RequestParam("loginAccount")String loginAccount,
                          @RequestParam("loginPassword")String loginPassword, HttpSession session){
        // 1.判断当前用户是否存在
        Admin admin = adminService.getAdminByAccountAndPassword(loginAccount, loginPassword);
        if (admin!=null){
            //2.将用户信息存放到session中。
            session.setAttribute("admin",admin);
        }
        //3.返回到主页面
//        return "page/admin-main";
        return "redirect:/admin/to/main.html";
        //重定向相当于重新发送请求，此时的请求没有经过验证无法直接访问WEB-INF文件夹
    }

    /**
     * 退出功能
     * 退出登陆操作，销毁session
     * @param session
     * @return
     */
    @RequestMapping("admin/do/logout.html")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:admin/to/login/index.html";
    }

    /**
     * 用户查询分页功能
     * 根据关键词进行分页
     * @param keyword   关键词
     * @param pageNum   页数
     * @param pageSize  记录数
     * @param map       请求域
     * @return
     */
    @RequestMapping("admin/get/page.html")
    public String getAdminPage(
            // 注意：页面上有可能不提供关键词，要进行适配
            // 在@RequestParam 注解中设置defaultValue 属性为空字符串表示浏览器不提供关键词时，keyword 变量赋值为空字符串
            @RequestParam(name = "keyword",defaultValue = "") String keyword,
            // 浏览器未提供pageNum 时，默认前往第一页
            @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
            // 浏览器未提供pageSize 时，默认每页显示5 条记录
            @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize,
            //将查询到的数据存放进模型
            ModelMap map
    ){
        // 查询得到分页数据
        PageInfo<Admin> pageInfo = adminService.getAdminPageInfo(keyword, pageNum, pageSize);

        //将分页数据存入模型
        map.addAttribute("pageInfo",pageInfo);

        return "page/admin-page";
    }

    /**
     * 用户删除功能
     * @param id
     * @param pageNum
     * @param keyword
     * @return
     */
    @RequestMapping("/admin/remove/{id}/{pageNum}/{keyword}.html")
    public String remove(
            @PathVariable("id")Integer id,
            @PathVariable("pageNum")Integer pageNum,
            @PathVariable("keyword")String keyword
    ){
        //第一步：通过adminServer调用用户进行用户移除

        adminService.remove(id);

        //第二步：通过信息进行重定向到分页查询
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    /**
     * 新增用户功能
     * @param loginAcct
     * @param userPswd
     * @param userName
     * @param email
     * @return
     */
    @RequestMapping("admin/save.html")
    public String save(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            @RequestParam("userName") String userName,
            @RequestParam("email") String email
    ){
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
        Admin admin = new Admin(null, loginAcct, userPswd, userName, email, createTime);
        log.debug("正在填加的用户为：{}",admin);
        //执行新增操作
        adminService.saveAdmin(admin);
        //重定向到分页处
        return "redirect:/admin/get/page.html?keyword="+loginAcct;
    }

    /**
     * 执行更新操作
     * @param admin
     * @return
     */
    @RequestMapping("admin/update.html")
    public String update(Admin admin){
        adminService.updateAdmin(admin);
        return "redirect:/admin/get/page.html?keyword="+admin.getLoginAcct();
    }

    /**
     * 请求跳转更新页面功能
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("/admin/to/update/page.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            HttpSession session,
            ModelMap modelMap
    ){
        //根据adminId查询对象
        Admin admin = adminService.getAdminById(adminId);
        //将Admin对象存放到作用域中
        modelMap.addAttribute("admin",admin);

        session.setAttribute("adminUpdate",admin);
        //controller转发页面就是统一请求，不改变请求地址
        return "page/admin-update";
    }
}
