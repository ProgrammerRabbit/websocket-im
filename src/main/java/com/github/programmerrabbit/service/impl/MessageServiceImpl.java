package com.github.programmerrabbit.service.impl;

import com.github.programmerrabbit.dao.MessageDao;
import com.github.programmerrabbit.dao.entity.Message;
import com.github.programmerrabbit.dto.MessageDto;
import com.github.programmerrabbit.dto.MessageQueryDto;
import com.github.programmerrabbit.service.MessageService;
import com.github.programmerrabbit.utils.CollectionUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rabbit on 2016/12/18.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageDao messageDao;

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    public void persistMessage(MessageDto messageDto) throws Exception {
        Message message = messageDto.toEntity();
        messageDao.insert(message);

        messageDto.setId(message.getId());
    }

    public List<MessageDto> queryMessageByQueryDto(MessageQueryDto queryDto) throws Exception {
        List<Message> messages = messageDao.getMessagesByQueryDto(queryDto);
        List<MessageDto> messageDtos = CollectionUtils.newArrayList();
        for (Message message : messages) {
            messageDtos.add(MessageDto.fromEntity(message));
        }
        return messageDtos;
    }

    public void sendWebSocketMessage(String destination, Object load) throws Exception {
        messagingTemplate.convertAndSend(destination, load);
    }
}
