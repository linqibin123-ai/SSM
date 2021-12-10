package com.dao;

import com.bean.Menu;

import java.util.List;

public interface MenuMapper {
    public List<Menu> getMenuList();
    public List<Menu> getMenuListByUserid(String id);
}
