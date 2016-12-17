package com.github.programmerrabbit.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Data
public class RequestDto implements Serializable {
    private int id;
    private int requestUserId;
    private String requestUserName;
    private int acceptUserId;
}
