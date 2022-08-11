package com.yama.crowd.service;

import com.yama.crowd.entity.Menu;

import java.util.List;

/**
 * 菜单模块业务层接口
 */
public interface MenuService {
    /**
     * 显示菜单列表功能
     * 查询所有的菜单信息
     * @return
     */
    List<Menu> getAllMenu();

    /**
     * 保存菜单功能
     * @param menu
     */
    void save(Menu menu);

    /**
     * 选择性更新菜单功能
     * @param menu
     */
    void update(Menu menu);

    /**
     * 删除菜单功能
     * @param id
     */
    void delete(int id);
}
