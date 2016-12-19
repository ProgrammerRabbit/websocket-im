package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.MessageDto;
import com.github.programmerrabbit.dto.MessageQueryDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.enums.MessageStatusEnum;
import com.github.programmerrabbit.enums.SortTypeEnum;
import com.github.programmerrabbit.service.ContactService;
import com.github.programmerrabbit.service.MessageService;
import com.github.programmerrabbit.utils.CollectionUtils;
import com.github.programmerrabbit.utils.SessionUtils;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Rabbit on 2016/12/19.
 */
@Controller
public class MessageController {
    @Resource
    private MessageService messageService;

    @Resource
    private ContactService contactService;

    @RequestMapping("/getHistoryMessages")
    @ResponseBody
    public ResponseDto<List<MessageDto>> getHistoryMessages(int userId, int contactUserId, HttpSession session) {
        ResponseDto<List<MessageDto>> responseDto = new ResponseDto<List<MessageDto>>();
        try {
            if (SessionUtils.isUserLegal(userId, session)) {
                MessageQueryDto queryDto = buildHistoryMessageQueryDto(userId, contactUserId);
                List<MessageDto> messages = messageService.getMessagesByQueryDto(queryDto);
                responseDto.setContent(messages);
            }
        } catch (Exception e) {
            e.printStackTrace();

            responseDto.setCode(500);
        }
        return responseDto;
    }

    @RequestMapping("/sendMessage")
    @ResponseBody
    public ResponseDto<Boolean> sendMessage(MessageDto message, HttpSession session) {
        ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();
        try {
            if (SessionUtils.isUserLegal(message.getFromId(), session)) {
                message.setAddTime(new Date());
                message.setStatus(MessageStatusEnum.SENT.getCode());
                messageService.persistMessage(message);

                String destination = "/topic/message/" + message.getToId();
                messageService.sendWebSocketMessage(destination, message);

                responseDto.setContent(true);
            }
        } catch (Exception e) {
            e.printStackTrace();

            responseDto.setCode(200);
        }
        return responseDto;
    }

    @RequestMapping("/getOfflineMessageCount")
    @ResponseBody
    public ResponseDto<List<ContactOffMsgCntPair>> getOfflineMessageCount(int userId, HttpSession session) {
        ResponseDto<List<ContactOffMsgCntPair>> responseDto = new ResponseDto<List<ContactOffMsgCntPair>>();
        try {
            if (SessionUtils.isUserLegal(userId, session)) {
                List<ContactOffMsgCntPair> contactOffMsgCntPairs = CollectionUtils.newArrayList();

                Set<AccountDto> contacts = contactService.getContacts(userId);
                for (AccountDto contact : contacts) {
                    MessageQueryDto queryDto = buildOfflineMessageCountQueryDto(userId, contact.getId());
                    int count = messageService.getMessagesCountByQueryDto(queryDto);
                    ContactOffMsgCntPair pair = new ContactOffMsgCntPair();
                    pair.setContactUserId(contact.getId());
                    pair.setCount(count);
                    contactOffMsgCntPairs.add(pair);
                }

                responseDto.setContent(contactOffMsgCntPairs);
            }
        } catch (Exception e) {
            e.printStackTrace();

            responseDto.setCode(500);
        }
        return responseDto;
    }

    @Data
    private class ContactOffMsgCntPair {
        private int contactUserId;
        private int count;
    }

    private MessageQueryDto buildHistoryMessageQueryDto(int userId, int contactUserId) {
        MessageQueryDto queryDto = new MessageQueryDto();
        queryDto.setFromId(contactUserId);
        queryDto.setToId(userId);
        queryDto.setStatus(MessageStatusEnum.ALL.getCode());
        queryDto.setSortType(SortTypeEnum.ASC.getCode());
        queryDto.setBothSide(true);
        return queryDto;
    }

    private MessageQueryDto buildOfflineMessageCountQueryDto(int userId, int contactUserId) {
        MessageQueryDto queryDto = new MessageQueryDto();
        queryDto.setFromId(contactUserId);
        queryDto.setToId(userId);
        queryDto.setStatus(MessageStatusEnum.SENT.getCode());
        return queryDto;
    }
}
