package com.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@Getter
@Setter
@ToString
public class User {
    private String id;
    private String pwd;
    private String email;
    private String name;
    private String department;
    private String job;
    private String sex;
    private String phone;
    private String address;
    private List<Role> roles = new ArrayList<Role>();
}
