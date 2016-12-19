package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.MessageDto;
import com.github.programmerrabbit.dto.MessageQueryDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.enums.MessageStatusEnum;
import com.github.programmerrabbit.enums.SortTypeEnum;
import com.github.programmerrabbit.service.MessageService;
import com.github.programmerrabbit.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Rabbit on 2016/12/19.
 */
@Controller
public class MessageController {
    @Resource
    private MessageService messageService;

    @RequestMapping("/getHistoryMessages")
    @ResponseBody
    public ResponseDto<List<MessageDto>> getHistoryMessages(int userId, int contactUserId, HttpSession session) {
        ResponseDto<List<MessageDto>> responseDto = new ResponseDto<List<MessageDto>>();
        try {
            if (SessionUtils.isUserLegal(userId, session)) {
                MessageQueryDto queryDto = buildMessageQueryDto(userId, contactUserId);
                List<MessageDto> messages = messageService.queryMessageByQueryDto(queryDto);
                responseDto.setContent(messages);
            }
        } catch (Exception e) {
            e.printStackTrace();

            responseDto.setCode(500);
        }
        return responseDto;
    }

    private MessageQueryDto buildMessageQueryDto(int userId, int contactUserId) {
        MessageQueryDto queryDto = new MessageQueryDto();
        queryDto.setFromId(contactUserId);
        queryDto.setToId(userId);
        queryDto.setStatus(MessageStatusEnum.SENT.getCode());
        queryDto.setSortType(SortTypeEnum.ASC.getCode());
        queryDto.setBothSide(true);
        return queryDto;
    }
}
