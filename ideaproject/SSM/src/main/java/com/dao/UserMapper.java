package com.dao;

import com.bean.Role;
import com.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    public  List<User> select(Map<String, Object> map);
    public  List<User> selectbyidandpwd(User user);
    public  List<User> selectbyid(User user);
    public  int updatepwd(User user);
    public  int insert(User user);
    public  int getTotalCount(Map<String, Object> map);
    public  int delete(String id);
    public  int updateUser(User user);
    public  Role getRoleById(String roleid);
}
