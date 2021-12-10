package com.service;

import com.bean.Category;
import com.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> selectAll() {
        return categoryMapper.selectAll();
    }

    public List<Category> selectbypager() {
        return categoryMapper.selectbypager();
    }

    public Category select(Category category) {
        return categoryMapper.selectByPrimaryKey(category.getId());
    }

    public int add(Category category) {
        return categoryMapper.insert(category);
    }

    public int delete(Category category) {
        return categoryMapper.deleteByPrimaryKey(category.getId());
    }

    public int update(Category category) {
        return categoryMapper.updateByPrimaryKey(category);
    }

    public Category selectByName(Category c) {
        return categoryMapper.selectByName(c);
    }
}
