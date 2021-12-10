package com.service;

import com.bean.Menu;
import com.dao.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> menuList(){
        return menuMapper.getMenuList();
    }
    public List<Menu> menuListByUserid(String id) { return menuMapper.getMenuListByUserid(id); }
}
