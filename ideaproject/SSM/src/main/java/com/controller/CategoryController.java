package com.controller;

import com.assist.StringUtil;
import com.assist.WebUtils;
import com.bean.Category;
import com.bean.Goods;
import com.bean.Summoney;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.CategoryService;
import com.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/categoryController")
@CrossOrigin(allowCredentials="true",allowedHeaders="*",origins = "*")
public class CategoryController {
    @Autowired
    private Category category;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    //新增种类
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String add() {
        String name = request.getParameter("name");
        Category category = new Category();
        category.setName(name);
        Category c = new Category();
        c.setName(name);
        try {
            c = categoryService.selectByName(c);
            if (c!=null) {
                return WebUtils.getDataByCodeAndMsg("2","重复新增");
            }
            categoryService.add(category);
            return WebUtils.getDataByCodeAndMsg("0","ok");
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","新增失败");
        }
    }

    //删除种类
    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String delete() {
        String id = request.getParameter("id");
        Category category = new Category();
        try {
            category.setId(Integer.parseInt(id));
            category = categoryService.select(category);
            Goods goods = new Goods();
            goods.setType(category.getName());
            List<Goods> goodsList = goodsService.selectByConditional(goods);
            if (!goodsList.isEmpty())
                return WebUtils.getDataByCodeAndMsg("2","存在商品禁止删除");
            categoryService.delete(category);
            return WebUtils.getDataByCodeAndMsg("0","ok");
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","查询失败");
        }
    }

    //修改种类
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String update() {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        Category category = new Category();
        try {
            Category c = new Category();
            c.setName(name);
            c = categoryService.selectByName(c);
            if (c!=null) {
                return WebUtils.getDataByCodeAndMsg("2","重复新增");
            }
            category.setId(Integer.parseInt(id));
            category.setName(name);
            categoryService.update(category);
            return WebUtils.getDataByCodeAndMsg("0","ok");
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","修改失败");
        }
    }

    //查询种类
    @RequestMapping(value = "/selectAll",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String selectAll() {
        try {
            List<Category> categoryList = categoryService.selectAll();
            return WebUtils.getDataByCodeAndMsgAndRs("0","ok",categoryList);
        }catch (Exception e){
            return WebUtils.getDataByCodeAndMsg("1","查询失败");
        }
    }

    //查询ById
    @RequestMapping(value = "/select",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String select() {
        String id = request.getParameter("id");
        Category category = new Category();
        try {
            category.setId(Integer.parseInt(id));
            category = categoryService.select(category);
            return WebUtils.getDataByCodeAndMsgAndRs("0","ok",category);
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","查询失败");
        }
    }

    //分页查询
    @RequestMapping(value = "/selectbypager",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String selectbypager() {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if (StringUtil.isEmpty(page))
            page="1";
        if(StringUtil.isEmpty(rows))
            rows="10";
        int int_page = Integer.parseInt(page);
        int int_rows = Integer.parseInt(rows);
        try {
            PageHelper.startPage(int_page,int_rows);
            List<Category> categoryList = categoryService.selectbypager();
            PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
            return WebUtils.getDataByCodeAndMsgAndRs("0","ok",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","查询失败");
        }
    }
}
