package com.service;

import com.dao.GoodsdetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class GoodsDetailsService {
    @Autowired
    private GoodsdetailsMapper goodsdetailsMapper;


}
