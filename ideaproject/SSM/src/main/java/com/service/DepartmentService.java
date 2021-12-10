package com.service;

import com.bean.Department;
import com.dao.DepartmentMapper;
import com.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    public List<Department> departmentList(){return departmentMapper.departmentList();}
}
