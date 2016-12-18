package com.github.programmerrabbit.dto;

import com.github.programmerrabbit.dao.entity.Request;
import com.github.programmerrabbit.utils.BeanUtils;
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

    public Request toEntity() {
        Request request = new Request();
        BeanUtils.copyProperties(this, request);
        return request;
    }

    public static RequestDto fromEntity(Request request) {
        RequestDto requestDto = new RequestDto();
        BeanUtils.copyProperties(request, requestDto);
        return requestDto;
    }
}
