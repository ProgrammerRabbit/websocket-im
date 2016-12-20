package com.github.programmerrabbit.service;

import com.github.programmerrabbit.dto.MessageDto;
import com.github.programmerrabbit.dto.MessageQueryDto;

import java.util.List;

/**
 * Created by Rabbit on 2016/12/18.
 */
public interface MessageService {
    List<MessageDto> getMessagesByQueryDto(MessageQueryDto queryDto) throws Exception;

    int getMessagesCountByQueryDto(MessageQueryDto queryDto) throws Exception;

    void readOfflineMessage(int userId, int contactUserId) throws Exception;

    void sendWebSocketMessage(String destination, Object load) throws Exception;

    void persistMessageAndSend(String destination, MessageDto messageDto) throws Exception;
}
