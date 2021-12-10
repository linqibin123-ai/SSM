package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.assist.WebUtils;
import com.bean.Count;
import com.bean.CountMoney;
import com.bean.CountStatus;
import com.bean.Goods;
import com.service.GoodsService;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
@RequestMapping(value = "/statisticalController")
@CrossOrigin(allowCredentials="true",allowedHeaders="*",origins = "*")
public class StatisticalController {
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    //获取消费统计
    @RequestMapping(value = "/getOrderStatus",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String getOrderStatus() {
        try {
            List<CountStatus> countStatusList = orderService.getCountStatus();
            HashMap<String,Integer> hashMap = new HashMap<>();
            for (CountStatus c:countStatusList) {
                if (c.getStatus().equals("2"))
                    hashMap.put("a",c.getNum());
                if (c.getStatus().equals("3"))
                    hashMap.put("b",c.getNum());
            }
            return WebUtils.getDataByCodeAndMsgAndRs("0","ok",hashMap);
        }catch (Exception e){
            e.printStackTrace();
            return WebUtils.getDataByCodeAndMsg("1","获取失败");
        }
    }

    //获取商品卖出总价统计
    @RequestMapping(value = "/getOrderMoney",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String getOrderMoney() {
        List<CountMoney> countMoneyList = orderService.getCountMonry();
        List<Map<String,Object>> hashMaps = new ArrayList<>();
        for (CountMoney c:countMoneyList) {
            Map<String,Object> hashmap = new HashMap<>();
            hashmap.put("goodsid",c.getGoodsid());
            Goods goods = goodsService.selectById(c.getGoodsid());
            hashmap.put("goodsname",goods.getMark());
            hashmap.put("money",c.getMoney());
            hashMaps.add(hashmap);
        }
        return WebUtils.getDataByCodeAndMsgAndRs("0","OK",hashMaps);
    }

    //获取商品卖出数量统计
    @RequestMapping(value = "/getOrderCount",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    private String getOrderCount() {
        List<Count> countMoneyList = orderService.getCount();
        List<Map<String,Object>> hashMaps = new ArrayList<>();
        for (Count c:countMoneyList) {
            Map<String,Object> hashmap = new HashMap<>();
            hashmap.put("goodsid",c.getGoodsid());
            Goods goods = goodsService.selectById(c.getGoodsid());
            hashmap.put("goodsname",goods.getMark());
            hashmap.put("count",c.getCount());
            hashMaps.add(hashmap);
        }
        return WebUtils.getDataByCodeAndMsgAndRs("0","OK",hashMaps);
    }
}
