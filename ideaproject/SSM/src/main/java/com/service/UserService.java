package com.service;

import com.assist.Email;
import com.bean.User;
import com.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class UserService {
    public UserService(){}

    @Autowired
    private UserMapper userMapper;

    public  int addUser(User user){return userMapper.insert(user);}

    public  User login(User user){
        List<User> userList = userMapper.selectbyidandpwd(user);
        if (userList.isEmpty())
            return null;
        else
            return userList.get(0);
    }

    public  int register(User user) {
        return userMapper.insert(user);
    }

    public  int updatepwd(User user){
        return userMapper.updatepwd(user);
    }

    public  boolean checkid(User user){
        List<User> userList = userMapper.selectbyid(user);
        if (userList.isEmpty())
            return false;
        else
            return true;
    }

    public  String getcode(String email){
        String code = Email.random();
        Email.send(email,code);
        return code;
    }

    public  boolean uppwdcheck(User user){
        List<User> userList = userMapper.selectbyid(user);
        User user1 = userList.get(0);
        System.out.println(user.getEmail());
        System.out.println(user1.getEmail());
        if (userList.isEmpty())
            return false;
        else if(!user1.getEmail().equals(user.getEmail()))
            return false;
        else
            return true;
    }

    public void senduppwd(String email,String url) {
        Email.senduppwd(email,url);
    }

    public List<User> getUserList(Map<String, Object> map){
        return userMapper.select(map);
    }

    public int getTotalCount(Map<String, Object> map) { return userMapper.getTotalCount(map);}

    public int delete(String id) { return userMapper.delete(id);}

    public List<User> selectById(User user) {
        return userMapper.selectbyid(user);
    }

    public int updateUser(User user) {return userMapper.updateUser(user); }
}
