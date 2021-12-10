package com.service;

import com.assist.WebUtils;
import com.bean.Summoney;
import com.dao.SummoneyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class SumMoneyService {
    @Autowired
    private SummoneyMapper summoneyMapper;

    public List<Summoney> selectbypager(Summoney summoney) {
        return summoneyMapper.selectByPager(summoney);
    }

    public int add(Summoney summoney) {
        return summoneyMapper.insert(summoney);
    }
}
