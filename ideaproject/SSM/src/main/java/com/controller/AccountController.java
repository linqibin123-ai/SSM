package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.assist.WebUtils;
import com.assist.sm4.SM4Util;
import com.bean.Account;
import com.bean.Order;
import com.service.AccountService;
import org.bouncycastle.jcajce.provider.symmetric.SM4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/accountController")
@PropertySource("classpath:config/jdbc.properties")
@CrossOrigin(allowCredentials="true",allowedHeaders="*",origins = "*")
public class AccountController {
    @Value("${driverClassName}")
    private String driverClassName;
    @Value("${url}")
    private String url;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;
    @Autowired
    private Account account;
    @Autowired
    private AccountService accountService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String register() throws Exception {
        Map<String,String> map = new HashMap<>();
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        pwd = SM4Util.encryptEcb(SM4Util.keyRec,pwd);
        account.setId(id);
        account.setPwd(pwd);
        account.setType("user");
        boolean flag = accountService.checkid(account);
        if (flag){
            account.setName("未知");
            account.setMobileno("");
            account.setSex(0);
            account.setMoney(0);
            account.setBirthday(new Date());
            account.setAddress("");
            accountService.register(account);
            map.put("code","0");
            map.put("msg","ok");
            return JSONObject.toJSONString(map);
        }else {
            map.put("code","1");
            map.put("msg","账号已存在");
            return JSONObject.toJSONString(map);
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String login() throws Exception {
        Map<String,Object> map = new HashMap<>();
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        pwd = SM4Util.encryptEcb(SM4Util.keyRec,pwd);
        if (id == null){
            map.put("code","1");
            map.put("msg","账号为空");
            return JSONObject.toJSONString(map);
        }
        if (pwd == null){
            map.put("code","1");
            map.put("msg","密码为空");
            return JSONObject.toJSONString(map);
        }
        Account account = new Account();
        account.setId(id);
        account.setPwd(pwd);
        account = accountService.login(account);
        if (account==null) {
            map.put("code","2");
            map.put("msg","账号或密码错误");
        } else {
            map.put("code","0");

            map.put("msg","ok");
            map.put("rs",account);
            session.setAttribute("user",account);
        }
        return JSONObject.toJSONString(map);
    }


}
