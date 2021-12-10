package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.assist.StringUtil;
import com.assist.WebUtils;
import com.bean.Account;
import com.bean.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/orderController")
@CrossOrigin(allowCredentials="true",allowedHeaders="*",origins = "*")
public class OrderController {
    @Autowired
    private Order order;
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    //确认发货
    @RequestMapping(value = "/deliver",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String deliver() {
        String id = request.getParameter("id");
        Order nowOrder = new Order();
        nowOrder.setId(Integer.parseInt(id));
        nowOrder = orderService.select(nowOrder);
        if (nowOrder.getStatus().equals("3")){
            return WebUtils.getDataByCodeAndMsg("1","已发货，请勿重复发货");
        }
        Order order = new Order();
        order.setId(Integer.parseInt(id));
        order.setStatus("3");
        orderService.update(order);
        return WebUtils.getDataByCodeAndMsg("0","ok");
    }

    //删除订单
    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String delete() {
        String id = request.getParameter("id");
        Order order = new Order();
        order.setId(Integer.parseInt(id));
        int i = orderService.deletebyid(order);
        if (i==1)
            return WebUtils.getDataByCodeAndMsg("0","ok");
        else
            return WebUtils.getDataByCodeAndMsg("1","删除失败");
    }

    //修改订单
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String update() {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String mobileno = request.getParameter("mobileno");
        String address = request.getParameter("address");
        Order order = new Order();
        order.setId(Integer.parseInt(id));
        order.setName(name);
        order.setMobileno(mobileno);
        order.setAddress(address);
        order.setUpdatedate(new Date());
        orderService.update(order);
        return WebUtils.getDataByCodeAndMsg("0","ok");
    }

    //查询订单
    @RequestMapping(value = "/select",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String select() {
        String id =request.getParameter("id");
        if (StringUtil.isEmpty(id))
            return WebUtils.getDataByCodeAndMsg("1","账号异常");
        Order order = new Order();
        order.setId(Integer.parseInt(id));
        order = orderService.select(order);
        return WebUtils.getDataByCodeAndMsgAndRs("0","ok",order);
    }

    //分页查询订单
    @RequestMapping(value = "/selectbypager",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String selectbypager() {
        String name = request.getParameter("name");
        String mobileno = request.getParameter("mobileno");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if (StringUtil.isEmpty(page))
            page="1";
        if(StringUtil.isEmpty(rows))
            rows="10";
        int int_page = Integer.parseInt(page);
        int int_rows = Integer.parseInt(rows);
        Account account = new Account();
        if (!StringUtil.isEmpty(name))
            account.setName(name);
        if (!StringUtil.isEmpty(mobileno))
            account.setMobileno(mobileno);
        PageHelper.startPage(int_page,int_rows);
        List<Order> orderList = orderService.selectbypager(account);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        Map resMap = new HashMap();
        resMap.put("code", 0);
        resMap.put("msg", "ok");
        resMap.put("rs", pageInfo);
        return JSON.toJSONString(resMap,SerializerFeature.DisableCircularReferenceDetect);
    }
}
