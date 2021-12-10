package com.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * order
 * @author 
 */
@Component
@Scope("prototype")
@Getter
@Setter
@ToString
public class Order implements Serializable {
    private Integer id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户手机
     */
    private String mobileno;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 创建时间
     */
    private Date creatdate;

    /**
     * 更新时间
     */
    private Date updatedate;

    /**
     * 订单状态（1:待支付2:待发货3:已发货4:已收货）
     */
    private String status;

    /**
     * 总金额
     */
    private Double money;

    /**
     * 商品编号
     */
    private Integer goodsid;

    /**
     * 商品名称
     */
    private Goods goods;

    /**
     * 购买数量
     */
    private Integer quantity;

    private static final long serialVersionUID = 1L;
}