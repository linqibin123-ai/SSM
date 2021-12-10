package com.service;

import com.bean.*;
import com.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public int insert(Order order) {
        return orderMapper.insertSelective(order);
    }

    public int update(Order order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    public List<Order> selectbypager(Account account) {
        return orderMapper.selectbypager(account);
    }

    public int deletebyid(Order order) {
        return orderMapper.deleteByPrimaryKey(order.getId());
    }

    public Order select(Order order) {
        return orderMapper.selectByPrimaryKey(order.getId());
    }

    public List<CountStatus> getCountStatus() {
        return orderMapper.getCountStatus();
    }

    public List<CountMoney> getCountMonry() {
        return orderMapper.getCountMoney();
    }

    public List<Count> getCount() {
        return orderMapper.getCount();
    }
}
