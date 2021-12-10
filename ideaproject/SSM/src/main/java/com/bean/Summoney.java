package com.bean;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * summoney
 * @author 
 */
@Component
@Scope("prototype")
@Getter
@Setter
@ToString
public class Summoney implements Serializable {
    private Integer id;

    private String accountid;

    private Double balance;

    private Double change;

    private Integer flag;

    private static final long serialVersionUID = 1L;
}