package com.service;

import com.dao.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
}