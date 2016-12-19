package com.github.programmerrabbit.service.impl;

import com.github.programmerrabbit.dao.MessageDao;
import com.github.programmerrabbit.dao.entity.Message;
import com.github.programmerrabbit.dto.MessageDto;
import com.github.programmerrabbit.service.MessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Rabbit on 2016/12/18.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageDao messageDao;

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    public void persistMessage(MessageDto messageDto) {
        Message message = messageDto.toEntity();
        messageDao.insert(message);

        messageDto.setId(message.getId());
    }

    public void sendWebSocketMessage(String destination, Object load) {
        messagingTemplate.convertAndSend(destination, load);
    }
}
