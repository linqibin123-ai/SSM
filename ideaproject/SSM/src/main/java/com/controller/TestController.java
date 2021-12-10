package com.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/testController")
@CrossOrigin(allowCredentials="true",allowedHeaders="*",origins = "*")
public class TestController {
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    private String test(MultipartFile file) {
        System.out.println("-----------");
        System.out.println(file.getOriginalFilename());
        return "";
    }


}
