package com.yama.crowd.mvc.handler;

import com.yama.crowd.entity.Menu;
import com.yama.crowd.service.MenuService;
import com.yama.crowd.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 菜单模块控制层接口实现类
 */
@Slf4j
//@Controller
//@ResponseBody
@RestController
public class MenuHandler {
    @Autowired
    private MenuService menuService;

    /**
     * 未优化前的实现方式，获取菜单数据
     * @return
     */
    public ResultUtil<Menu> getWholeMenuTreeOld(){
        //获取所有菜单
        List<Menu> allMenu = menuService.getAllMenu();
        //根节点
        Menu root = null;
        //封装根节点，以及其他节点
        for (Menu menu:allMenu){
            //获取当前menu父节点
            Integer pid = menu.getPid();
            //判断是否为根节点
            if(pid==null){
                root = menu;
                continue;
            }
            //从集合中查询当前节点的父节点
            for (Menu parent:allMenu){
                Integer parentId = parent.getId();
                if (Objects.equals(parentId,pid)){
                    //当前遍历的节点为父节点
                    parent.getChildren().add(menu);
                }
            }
        }
        return ResultUtil.ok(root);
    }

    /**
     * 显示菜单列表功能
     * @return 返回根节点，其中list成员变量存储其子节点，单个根节点可以遍历得到这个菜单树
     * 原本不使用hashmap，而是用嵌套for循环实现，时间复杂度（O^2）
     * 当前实现不使用嵌套循环，为（2O）,
     * 如果for循环的次数是1w，这种代码优化还是非常提高性能的
     */
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultUtil<Menu> getWholeMenuTree(){
        //获取所有菜单
        List<Menu> allMenu = menuService.getAllMenu();
        //根节点
        Menu root = null;
        //设置一个map容器用来保存所有容器，便于添加字节点
        HashMap<Integer,Menu> menuMap = new HashMap<>();
        for (Menu menu:allMenu){
            //作为键
            Integer key = menu.getId();
            menuMap.put(key,menu);
        }
        //封装根节点，以及其他节点
        for (Menu menu:allMenu){
            //获取当前menu父节点
            Integer pid = menu.getPid();
            //判断是否为根节点
            if(pid==null){
                root = menu;
                continue;
            }
            //不是根节点，从map中查询到父节点
            Menu parent = menuMap.get(pid);
            //将此节点添加到当前节点的父节点的list集合中
            parent.getChildren().add(menu);
        }
        log.debug("请求返回的根节点为：{}",root);
        return ResultUtil.ok(root);
    }

    /**
     * 保存菜单功能
     * @param menu
     * @return
     */
    @RequestMapping("/menu/save.json")//“/"相当于请求转发中的Ip:端口号/项目名/
    public ResultUtil<String> saveMenu(@RequestBody Menu menu){
        log.debug("当前对象未:{}",menu);
        menuService.save(menu);
        return ResultUtil.ok(null);
    }

    /**
     * 更新菜单模块功能
     * @param menu
     * @return
     */
    @RequestMapping("menu/update.json")
    public ResultUtil<String> updateMenu( Menu menu){
        menuService.update(menu);
        return ResultUtil.ok(null);
    }

    /**
     * 删除菜单模块功能
     * @param id
     * @return
     */
    @RequestMapping("menu/remove.json")
    public ResultUtil<String> removeMenu(@RequestParam("id")int id){
        menuService.delete(id);
        return ResultUtil.ok(null);
    }

}
