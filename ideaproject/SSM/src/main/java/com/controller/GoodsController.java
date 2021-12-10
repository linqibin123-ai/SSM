package com.controller;

import com.assist.StringUtil;
import com.assist.WebUtils;
import com.bean.Goods;
import com.bean.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/goodsController")
@CrossOrigin(allowCredentials="true",allowedHeaders="*",origins = "*")
public class GoodsController {
    @Autowired
    private Goods goods;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/selectByConditional",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String selectByConditional() {
        String type = request.getParameter("type");
        if (StringUtil.isEmpty(type))
            return WebUtils.getDataByCodeAndMsg("1","参数丢失");
        Goods goods = new Goods();
        goods.setType(type);
        List<Goods> goodsList = goodsService.selectByConditional(goods);
        return WebUtils.getDataByCodeAndMsgAndRs("0","ok",goodsList);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String add(){
        String imagestr = request.getParameter("image");
        String mark = request.getParameter("mark");
        String m= request.getParameter("money");
        String type = request.getParameter("type");
        String s = request.getParameter("stock");
        String details = request.getParameter("details");
        System.out.println(details);
        double money = Double.parseDouble(m);
        Integer stock = Integer.parseInt(s);
        Goods goods = new Goods();
        goods.setImage(imagestr);
        goods.setMark(mark);
        goods.setMoney(money);
        goods.setType(type);
        goods.setStock(stock);
        goods.setDetails(details);
        try {
            goodsService.add(goods);
            return WebUtils.getDataByCodeAndMsg("0","ok");
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","插入失败");
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String delete(){
        String id = request.getParameter("id");
        if (id==null){
            return WebUtils.getDataByCodeAndMsg("1","参数丢失");
        }
        try {
            Goods goods = new Goods();
            goods.setId(Integer.parseInt(id));
            goodsService.delete(goods);
            return WebUtils.getDataByCodeAndMsg("0","ok");
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("2","删除失败");
        }
    }

    @RequestMapping(value = "/select",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String select(){
        String id = request.getParameter("id");
        if (StringUtil.isEmpty(id)){
            return WebUtils.getDataByCodeAndMsg("1","参数丢失");
        }
        try {
            Goods goods = goodsService.selectById(Integer.parseInt(id));
            return WebUtils.getDataByCodeAndMsgAndRs("0","ok",goods);
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("2","查询失败");
        }
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String update(){
        String id = request.getParameter("id");
        if (StringUtil.isEmpty(id)){
            return WebUtils.getDataByCodeAndMsg("1","参数丢失");
        }
        String image = request.getParameter("image");
        String mark = request.getParameter("mark");
        String m= request.getParameter("money");
        String type = request.getParameter("type");
        String s = request.getParameter("stock");
        String details = request.getParameter("details");
        System.out.println(details);
        double money = Double.parseDouble(m);
        Integer stock = Integer.parseInt(s);
        Goods goods = new Goods();
        goods.setId(Integer.parseInt(id));
        goods.setImage(image);
        goods.setMark(mark);
        goods.setMoney(money);
        goods.setType(type);
        goods.setStock(stock);
        goods.setDetails(details);
        try {
            goodsService.update(goods);
            return WebUtils.getDataByCodeAndMsg("0","ok");
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("2","更新失败");
        }
    }

    @RequestMapping(value = "/selectbypager",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String selectbypager(){
        String type = request.getParameter("type");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if (StringUtil.isEmpty(page))
            page="1";
        if(StringUtil.isEmpty(rows))
            rows="10";
        int int_page = Integer.parseInt(page);
        int int_rows = Integer.parseInt(rows);
        Goods goods = new Goods();
        if (!StringUtil.isEmpty(type))
            goods.setType(type);
        try {
            PageHelper.startPage(int_page,int_rows);
            List<Goods> goodsList = goodsService.selectbypager(goods);
            PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
            return WebUtils.getDataByCodeAndMsgAndRs("0","ok",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","查询失败");
        }
    }
}
