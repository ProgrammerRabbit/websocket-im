package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.MessageDto;
import com.github.programmerrabbit.dto.RequestDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.enums.MessageStatusEnum;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.service.MessageService;
import com.github.programmerrabbit.service.RequestService;
import com.github.programmerrabbit.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Controller
public class RequestController {
    @Resource
    private RequestService requestService;

    @Resource
    private AccountService accountService;

    @Resource
    private MessageService messageService;

    @RequestMapping("/getRequests")
    @ResponseBody
    public ResponseDto<List<RequestDto>> getRequests(HttpSession session) {
        ResponseDto<List<RequestDto>> responseDto = new ResponseDto<List<RequestDto>>();

        try {
            AccountDto loginAccount = SessionUtils.getLoginAccount(session);
            if (loginAccount != null) {
                List<RequestDto> requests = requestService.getRequestsByUserId(loginAccount.getId());
                responseDto.setContent(requests);
            }
        } catch (Exception e) {
            e.printStackTrace();

            responseDto.setCode(500);
        }

        return responseDto;
    }

    @RequestMapping("/rejectRequest")
    @ResponseBody
    public ResponseDto<Boolean> rejectRequest(int requestId, int userId, HttpSession session) {
        ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();
        try {
            if (SessionUtils.isUserLegal(userId, session)) {
                requestService.rejectRequest(requestId);

                responseDto.setContent(true);
            } else {
                responseDto.setContent(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setCode(500);
        }
        return responseDto;
    }

    @RequestMapping("/acceptRequest")
    @ResponseBody
    public ResponseDto<Boolean> acceptRequest(int requestId, int userId, HttpSession session) {
        ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();
        try {
            if (SessionUtils.isUserLegal(userId, session)) {
                RequestDto requestInfo = requestService.acceptRequest(requestId);
                updateContacts(session, requestInfo.getRequestUserId());
                sendAcceptMessage(requestInfo);

                responseDto.setContent(true);
            } else {
                responseDto.setContent(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setCode(500);
        }
        return responseDto;
    }

    private void updateContacts(HttpSession session, int requestUserId) throws Exception {
        AccountDto newContact = accountService.getSimpleAccountById(requestUserId);
        AccountDto loginAccount = (AccountDto) session.getAttribute("s_user");
        loginAccount.getContacts().add(newContact);
        session.setAttribute("s_user", loginAccount);
    }

    private void sendAcceptMessage(RequestDto requestInfo) throws Exception {
        String destination = "/topic/message/" + requestInfo.getRequestUserId();

        MessageDto messageDto = new MessageDto();
        messageDto.setAddTime(new Date());
        messageDto.setFromId(requestInfo.getAcceptUserId());
        messageDto.setToId(requestInfo.getRequestUserId());
        messageDto.setContent("I accept your request, let's chat.");
        messageDto.setStatus(MessageStatusEnum.SENT.getCode());

        messageService.persistMessageAndSend(destination, messageDto);
    }
}
