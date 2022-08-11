package com.yama.crowd.service.impl;

import com.yama.crowd.entity.Menu;
import com.yama.crowd.entity.MenuExample;
import com.yama.crowd.mapper.MenuMapper;
import com.yama.crowd.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 菜单模块业务层接口实现类
 */
@Slf4j
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 显示菜单列表信息
     * 查询所有的菜单信息
     * @return
     */
    @Override
    public List<Menu> getAllMenu() {
        MenuExample menuExample = new MenuExample();
        List<Menu> menus = menuMapper.selectByExample(menuExample);
        return menus;
    }

    /**
     * 保存菜单功能
     * @param menu
     */
    @Override
    public void save(Menu menu) {
        menuMapper.insert(menu);
    }

    /**
     * 选择性更新菜单功能
     * @param menu
     */
    @Override
    public void update(Menu menu) {
        //选择性更新字段，pid字段默认不更新
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 删除菜单功能
     * @param id
     */
    @Override
    public void delete(int id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
