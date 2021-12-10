package com.bean;

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
public class Menu {
    private String id;
    private String name;
    private String url;
    private String parentid;
    private int grade;
    private int isleaf;
}
