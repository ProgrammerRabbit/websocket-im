package com.github.programmerrabbit.dto;

import lombok.Data;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Data
public class ResponseDto<T> {
    private T content;
    private int code = 200;
}
