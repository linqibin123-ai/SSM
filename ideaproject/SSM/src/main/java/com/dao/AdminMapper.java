package com.dao;

import com.bean.Admin;
import com.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {
    public List<Admin> selectbyidandpwd(Admin admin);
}
