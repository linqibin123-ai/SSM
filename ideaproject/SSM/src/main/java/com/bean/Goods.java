package com.bean;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * goods
 * @author 
 */
@Component
@Scope("prototype")
@Getter
@Setter
@ToString
public class Goods implements Serializable {
    private Integer id;

    /**
     * 图片
     */
    private String image;

    /**
     * 描述
     */
    private String mark;

    /**
     * 价格
     */
    private double money;

    /**
     * 类型
     */
    private String type;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 商品详情
     */
    private String details;

    private static final long serialVersionUID = 1L;
}