package com.github.programmerrabbit.service;

import com.github.programmerrabbit.dto.MessageDto;
import com.github.programmerrabbit.dto.MessageQueryDto;

import java.util.List;

/**
 * Created by Rabbit on 2016/12/18.
 */
public interface MessageService {
    void persistMessage(MessageDto messageDto) throws Exception;

    List<MessageDto> queryMessageByQueryDto(MessageQueryDto queryDto) throws Exception;

    void sendWebSocketMessage(String destination, Object load) throws Exception;
}
