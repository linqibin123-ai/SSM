package com.dao;

import com.bean.*;

import java.util.HashMap;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectbypager(Account account);

    Goods getGoods(int goodsid);

    List<CountStatus> getCountStatus();

    List<CountMoney> getCountMoney();

    List<Count> getCount();
}