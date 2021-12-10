package com.dao;

import com.bean.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMapper {
    public List<Department> departmentList();
}
