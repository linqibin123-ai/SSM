package com.bean;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * goodsdetails
 * @author 
 */
@Component
@Scope("prototype")
@Getter
@Setter
@ToString
public class Goodsdetails implements Serializable {
    private Integer id;

    private String introduction;

    private String pictures;

    private static final long serialVersionUID = 1L;
}