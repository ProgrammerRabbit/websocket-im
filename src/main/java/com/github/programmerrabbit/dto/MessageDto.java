package com.github.programmerrabbit.dto;

import com.github.programmerrabbit.dao.entity.Message;
import com.github.programmerrabbit.utils.BeanUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rabbit on 2016/12/18.
 */
@Data
public class MessageDto implements Serializable {
    private int id;
    private Date addTime;
    private int fromId;
    private String fromUserName;
    private int toId;
    private String content;
    private int status;

    public Message toEntity() {
        Message message = new Message();
        BeanUtils.copyProperties(this, message);
        return message;
    }

    public static MessageDto fromEntity(Message message) {
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        return messageDto;
    }
}
