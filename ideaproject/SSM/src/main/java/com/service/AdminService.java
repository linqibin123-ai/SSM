package com.service;

import com.bean.Admin;
import com.bean.User;
import com.dao.AdminMapper;
import com.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public Admin login(Admin admin){
        List<Admin> adminList = adminMapper.selectbyidandpwd(admin);
        if (adminList.isEmpty())
            return null;
        else
            return adminList.get(0);
    }
}
