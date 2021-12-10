package com.dao;

import com.bean.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMapper {
    public int getTotalCount(Map<String, Object> map);
    public List<Role> getRoleList(Map<String, Object> map);
    public Role selectById(String roleid);
    public int updateRole(Role role);
    public int insertRole(Role role);
    public int delete(String roleid);
    public String getWxName(String openid);
}
