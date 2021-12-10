package com.dao;

import com.bean.Summoney;

import java.util.List;

public interface SummoneyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Summoney record);

    int insertSelective(Summoney record);

    Summoney selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Summoney record);

    int updateByPrimaryKey(Summoney record);

    List<Summoney> selectByPager(Summoney summoney);
}