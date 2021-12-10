package com.bean;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * category
 * @author 
 */
@Component
@Scope("prototype")
@Getter
@Setter
@ToString
public class Category implements Serializable {
    private Integer id;

    private String name;

    private static final long serialVersionUID = 1L;
}