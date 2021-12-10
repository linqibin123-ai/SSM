package com.service;

import com.bean.Goods;
import com.dao.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    //条件查询商品列表
    public List<Goods> selectByConditional(Goods goods) {
        return goodsMapper.selectSelective(goods);
    }

    public Goods selectById(Integer goodsid) {
        return goodsMapper.selectByPrimaryKey(goodsid);
    }

    public int update(Goods goods) {
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public int add(Goods goods) {
        return goodsMapper.insert(goods);
    }

    public int delete(Goods goods) {
        return goodsMapper.deleteByPrimaryKey(goods.getId());
    }

    public List<Goods> selectbypager(Goods goods) {
        return goodsMapper.selectSelective(goods);
    }
}
