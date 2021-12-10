package com.dao;

import com.bean.Goodsdetails;

public interface GoodsdetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goodsdetails record);

    int insertSelective(Goodsdetails record);

    Goodsdetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goodsdetails record);

    int updateByPrimaryKey(Goodsdetails record);
}