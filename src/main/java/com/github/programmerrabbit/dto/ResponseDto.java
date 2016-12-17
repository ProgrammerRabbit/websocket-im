package com.github.programmerrabbit.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Data
public class ResponseDto<T> implements Serializable {
    private T content;
    private int code = 200;
    private String message = "";
}
