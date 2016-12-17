package com.github.programmerrabbit.service;

import com.github.programmerrabbit.dto.RequestDto;

import java.util.List;

/**
 * Created by Rabbit on 2016/12/17.
 */
public interface RequestService {
    List<RequestDto> getRequestsById(int id) throws Exception;

    void rejectRequest(int requestId) throws Exception;

    void acceptRequest(int requestId) throws Exception;

    void addRequest(RequestDto requestDto) throws Exception;
}
