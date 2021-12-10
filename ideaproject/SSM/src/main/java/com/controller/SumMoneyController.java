package com.controller;

import com.assist.StringUtil;
import com.assist.WebUtils;
import com.bean.Account;
import com.bean.Summoney;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.AccountService;
import com.service.SumMoneyService;
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
@RequestMapping(value = "/sumMoneyController")
@CrossOrigin(allowCredentials="true",allowedHeaders="*",origins = "*")
public class SumMoneyController {
    @Autowired
    private Summoney summoney;
    @Autowired
    private SumMoneyService sumMoneyService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    //分页查询消费记录
    @RequestMapping(value = "/selectbypager",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String selectbypager() {
        String accountid = request.getParameter("accountid");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if (StringUtil.isEmpty(page))
            page="1";
        if(StringUtil.isEmpty(rows))
            rows="10";
        int int_page = Integer.parseInt(page);
        int int_rows = Integer.parseInt(rows);
        try {
            Summoney summoney = new Summoney();
            if (!StringUtil.isEmpty(accountid))
                summoney.setAccountid(accountid);
            PageHelper.startPage(int_page,int_rows);
            List<Summoney> summoneyList = sumMoneyService.selectbypager(summoney);
            PageInfo<Summoney> pageInfo = new PageInfo<>(summoneyList);
            return WebUtils.getDataByCodeAndMsgAndRs("0","ok",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","查询失败");
        }

    }
}
