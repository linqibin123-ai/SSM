package com.controller;

import com.bean.Goodsdetails;
import com.service.GoodsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/goodsDetailsController")
@CrossOrigin(allowCredentials="true",allowedHeaders="*",origins = "*")
public class GoodsDetailsController {
    @Autowired
    private Goodsdetails goodsdetails;
    @Autowired
    private GoodsDetailsService goodsDetailsService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/selectById",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String register() throws Exception {
        String id =request.getParameter("id");

        return "";
    }
}
