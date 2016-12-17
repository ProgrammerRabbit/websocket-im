package com.github.programmerrabbit.dto;

import lombok.Data;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Data
public class RequestDto {
    private int id;
    private int requestUserId;
    private String requestUserName;
    private int acceptUserId;
    private int status;
}
