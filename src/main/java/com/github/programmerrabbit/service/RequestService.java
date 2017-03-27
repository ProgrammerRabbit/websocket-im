package com.github.programmerrabbit.service;

import com.github.programmerrabbit.dto.RequestDto;

import java.util.List;

/**
 * Created by Rabbit on 2016/12/17.
 */
public interface RequestService {
    List<RequestDto> getRequestsByUserId(int userId) throws Exception;

    void rejectRequest(int requestId) throws Exception;

    RequestDto acceptRequest(int requestId) throws Exception;

    void persistRequest(RequestDto requestDto) throws Exception;

    boolean isRequestExist(int requestUserId, int acceptUserId) throws Exception;

    RequestDto getRequestById(int id) throws Exception;
}
