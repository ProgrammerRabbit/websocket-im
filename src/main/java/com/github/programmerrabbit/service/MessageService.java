package com.github.programmerrabbit.service;

import com.github.programmerrabbit.dto.MessageDto;

/**
 * Created by Rabbit on 2016/12/18.
 */
public interface MessageService {
    void persistMessage(MessageDto messageDto);
}
