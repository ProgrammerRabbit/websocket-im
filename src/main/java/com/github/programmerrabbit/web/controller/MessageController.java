package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.MessageDto;
import com.github.programmerrabbit.dto.MessageQueryDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.enums.MessageStatusEnum;
import com.github.programmerrabbit.enums.SortTypeEnum;
import com.github.programmerrabbit.service.AccountService;
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
    private AccountService accountService;

    @Resource
    private ContactService contactService;

    @RequestMapping("/getHistoryMessages")
    @ResponseBody
    public ResponseDto<List<MessageDto>> getHistoryMessages(int contactUserId, HttpSession session) {
        ResponseDto<List<MessageDto>> responseDto = new ResponseDto<List<MessageDto>>();
        try {
            AccountDto loginAccount = SessionUtils.getLoginAccount(session);

            MessageQueryDto queryDto = buildHistoryMessageQueryDto(loginAccount.getId(), contactUserId);
            List<MessageDto> messages = messageService.getMessagesByQueryDto(queryDto);
            responseDto.setContent(messages);
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
            AccountDto loginAccount = SessionUtils.getLoginAccount(session);
            AccountDto contactAccount = accountService.getSimpleAccountById(message.getToId());
            if (loginAccount.getContacts().contains(contactAccount)) {
                String destination = "/topic/message/" + message.getToId();
                message.setFromId(loginAccount.getId());
                message.setAddTime(new Date());
                message.setStatus(MessageStatusEnum.SENT.getCode());

                messageService.persistMessageAndSend(destination, message);

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
    public ResponseDto<List<ContactOffMsgCntPair>> getOfflineMessageCount(HttpSession session) {
        ResponseDto<List<ContactOffMsgCntPair>> responseDto = new ResponseDto<List<ContactOffMsgCntPair>>();
        try {
            AccountDto loginAccount = SessionUtils.getLoginAccount(session);

            List<ContactOffMsgCntPair> contactOffMsgCntPairs = CollectionUtils.newArrayList();

            Set<AccountDto> contacts = contactService.getContacts(loginAccount.getId());
            for (AccountDto contact : contacts) {
                MessageQueryDto queryDto = buildOfflineMessageCountQueryDto(loginAccount.getId(), contact.getId());
                int count = messageService.getMessagesCountByQueryDto(queryDto);
                ContactOffMsgCntPair pair = new ContactOffMsgCntPair();
                pair.setContactUserId(contact.getId());
                pair.setCount(count);
                contactOffMsgCntPairs.add(pair);
            }

            responseDto.setContent(contactOffMsgCntPairs);
        } catch (Exception e) {
            e.printStackTrace();

            responseDto.setCode(500);
        }
        return responseDto;
    }

    @RequestMapping("/readOfflineMessages")
    @ResponseBody
    public ResponseDto<Boolean> readOfflineMessages(int contactUserId, HttpSession session) {
        ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();
        try {
            AccountDto loginAccount = SessionUtils.getLoginAccount(session);

            messageService.readOfflineMessage(loginAccount.getId(), contactUserId);

            responseDto.setContent(true);
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
