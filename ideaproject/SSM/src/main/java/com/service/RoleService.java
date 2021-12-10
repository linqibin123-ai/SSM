package com.service;

import com.bean.Role;
import com.bean.User;
import com.dao.RoleMapper;
import com.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public int getTotalCount(Map<String, Object> map) {
        return roleMapper.getTotalCount(map);
    }

    public List<Role> getRoleList(Map<String, Object> map) {
        return roleMapper.getRoleList(map);
    }

    public Role selectById(String roleid){return roleMapper.selectById(roleid);}

    public int updateRole(Role role) { return roleMapper.updateRole(role);}

    public int insertRole(Role role) { return roleMapper.insertRole(role);}

    public int delete(String roleid) { return roleMapper.delete(roleid);}

}
