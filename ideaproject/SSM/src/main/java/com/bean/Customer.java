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
 * customer
 * @author 
 */
@Component
@Scope("prototype")
@Getter
@Setter
@ToString
public class Customer implements Serializable {
    private Integer id;

    private String name;

    private String mobile;

    private String address;

    private Integer points;

    private Integer level;

    private Integer petsnumber;

    private Double money;

    private Date creattime;

    private String creatby;

    private Date updatetime;

    private String updateby;

    private static final long serialVersionUID = 1L;
}