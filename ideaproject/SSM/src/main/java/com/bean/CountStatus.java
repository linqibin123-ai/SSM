package com.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope("prototype")
@Getter
@Setter
@ToString
public class CountStatus implements Serializable {
    private String status;
    private int num;

    private static final long serialVersionUID = 1L;
}
