package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.assist.DateUtil;
import com.assist.StringUtil;
import com.assist.WebUtils;
import com.bean.Account;
import com.bean.Goods;
import com.bean.Order;
import com.bean.Summoney;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.AccountService;
import com.service.GoodsService;
import com.service.OrderService;
import com.service.SumMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Transactional
@RequestMapping(value = "/userInfoController")
@CrossOrigin(allowCredentials="true",allowedHeaders="*",origins = "*")
public class UserInfoController {
    @Autowired
    private Account account;
    @Autowired
    private AccountService accountService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SumMoneyService sumMoneyService;
    //删除用户
    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String delete() {
        String id = request.getParameter("id");
        try {
            Account account = new Account();
            account.setId(id);
            account = accountService.select(account);
            if (!account.getType().equals("admin")) {
                accountService.delete(id);
                return WebUtils.getDataByCodeAndMsg("0", "ok");
            }else {
                return WebUtils.getDataByCodeAndMsg("1","管理员账号无法删除");
            }
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","删除失败,未知异常");
        }
    }

    //添加用户
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String add() {
        String id = request.getParameter("id");
        if (StringUtil.isEmpty(id)){
            return WebUtils.getDataByCodeAndMsg("1","参数丢失");
        }
        String pwd = request.getParameter("pwd");
        Account account = new Account();
        account.setId(id);
        account.setPwd(pwd);
        account.setType("user");
        try {
            accountService.insert(account);
            return WebUtils.getDataByCodeAndMsg("0","ok");
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("2","未知异常");
        }
    }

    //更新个人信息
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String update() {
        Account user = (Account) session.getAttribute("user");
        String id = user.getId();
        if (id==null){
            return WebUtils.getDataByCodeAndMsg("1","账号异常");
        }
        String name = request.getParameter("name");
        String mobileno = request.getParameter("mobileno");
        String s = request.getParameter("sex");
        String b = request.getParameter("birthday");
        String address = request.getParameter("address");
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setMobileno(mobileno);
        if (s != null){
            int sex = Integer.parseInt(s);
            account.setSex(sex);
        }
        if(b != null){
            Date birthday = DateUtil.parseDate(b);
            account.setBirthday(birthday);
        }
        account.setAddress(address);
        accountService.update(account);
        return WebUtils.getDataByCodeAndMsg("0","ok");
    }

    //更新用户信息
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String updateUser() throws ParseException {
        String id = request.getParameter("id");
        if (id==null){
            return WebUtils.getDataByCodeAndMsg("1","参数丢失");
        }
        String name = request.getParameter("name");
        String mobileno = request.getParameter("mobileno");
        String s = request.getParameter("sex");
        String b = request.getParameter("birthday");
        String address = request.getParameter("address");
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setMobileno(mobileno);
        if (s != null){
            int sex = Integer.parseInt(s);
            account.setSex(sex);
        }
        if(b != null){
            Long l = Long.valueOf(b);
            Date birthday = new Date(l);
            System.out.println(birthday);
            account.setBirthday(birthday);
        }
        account.setAddress(address);
        accountService.update(account);
        return WebUtils.getDataByCodeAndMsg("0","ok");
    }

    //查询个人信息
    @RequestMapping(value = "/select",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String select() {
        System.out.println(session.getId());
        Account user = (Account) session.getAttribute("user");
        String id = user.getId();
        if (id==null){
            return WebUtils.getDataByCodeAndMsg("1","账号异常");
        }
        Account account = new Account();
        account.setId(id);
        account = accountService.select(account);
        if (account == null){
            return WebUtils.getDataByCodeAndMsg("2","账号不存在");
        }else {
            return WebUtils.getDataByCodeAndMsgAndRs("0","ok", account);
        }
    }

    //查询用户信息
    @RequestMapping(value = "/selectUser",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String selectUser() {
        String id = request.getParameter("id");
        if (id==null){
            return WebUtils.getDataByCodeAndMsg("1","账号异常");
        }
        Account account = new Account();
        account.setId(id);
        account = accountService.select(account);
        if (account == null){
            return WebUtils.getDataByCodeAndMsg("2","账号不存在");
        }else {
            return WebUtils.getDataByCodeAndMsgAndRs("0","ok", account);
        }
    }

    //查询用户列表
    @RequestMapping(value = "/selectbypager",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String selectbypager() {
        String id = request.getParameter("id");
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
        if (!StringUtil.isEmpty(id))
            account.setId(id);
        if (!StringUtil.isEmpty(name))
            account.setName(name);
        if (!StringUtil.isEmpty(mobileno))
            account.setMobileno(mobileno);
        PageHelper.startPage(int_page,int_rows);
        List<Account> accountList = accountService.selectbypager(account);
        PageInfo<Account> pageInfo = new PageInfo<Account>(accountList);
        return WebUtils.getDataByCodeAndMsgAndRs("0","ok",pageInfo);
    }

    @RequestMapping(value = "/buy",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String buy() {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String mobileno = request.getParameter("mobileno");
        String address = request.getParameter("address");
        String mey = request.getParameter("money");
        String gid = request.getParameter("goodsid");
        String qty = request.getParameter("quantity");
        String status = "2";
        Date creatdate = new Date();
        try {
            Integer goodsid = Integer.parseInt(gid);
            Integer quantity = Integer.parseInt(qty);
            Goods goods = goodsService.selectById(goodsid);
            Integer stock = goods.getStock() - quantity;
            if(stock<0)
                return WebUtils.getDataByCodeAndMsg("2","库存不足");
            goods.setStock(stock);
            goodsService.update(goods);

            Double money = Double.parseDouble(mey);
            Order order = new Order();
            order.setName(name);
            order.setAddress(address);
            order.setMobileno(mobileno);
            order.setMoney(money);
            order.setGoodsid(goodsid);
            order.setQuantity(quantity);
            order.setStatus(status);
            order.setCreatdate(creatdate);
            orderService.insert(order);

            Account account = new Account();
            account.setId(id);
            account = accountService.select(account);
            if(account.getMoney() - money < 0)
                return WebUtils.getDataByCodeAndMsg("3","金额不足，支付失败");
            account.setMoney(account.getMoney() - money);
            accountService.updateMoney(account);
            Summoney summoney = new Summoney();
            summoney.setAccountid(id);
            summoney.setBalance(account.getMoney());
            summoney.setChange(money);
            summoney.setFlag(0);
            sumMoneyService.add(summoney);
            return WebUtils.getDataByCodeAndMsg("0","ok");
        } catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","未知异常");
        }
    }

    @RequestMapping(value = "/recharge",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String recharge() {
        String id = request.getParameter("id");
        String m = request.getParameter("money");
        double money = Double.parseDouble(m);
        try {
            Account account = new Account();
            account.setId(id);
            account = accountService.select(account);
            account.setMoney(account.getMoney() + money);
            accountService.updateMoney(account);
            Summoney summoney = new Summoney();
            summoney.setAccountid(id);
            summoney.setBalance(account.getMoney());
            summoney.setChange(money);
            summoney.setFlag(1);
            sumMoneyService.add(summoney);
            return WebUtils.getDataByCodeAndMsg("0","ok");
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","充值失败");
        }

    }
}
