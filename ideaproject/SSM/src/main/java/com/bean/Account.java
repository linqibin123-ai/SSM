package com.bean;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@Scope("prototype")
@Getter
@Setter
@ToString
@JSONType(orders={"id","pwd","type","name","mobileno","sex","birthday","money","address"})
public class Account {
    private String id;
    private String pwd;
    private String type;
    private String name;
    private String mobileno;
    private int sex;
    private Date birthday;
    private double money;
    private String address;
}
