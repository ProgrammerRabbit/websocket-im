package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.RequestDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.service.RequestService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Controller
public class RequestController extends BaseController {
    @Resource
    private RequestService requestService;

    @Resource
    private AccountService accountService;

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping("/sendRequest")
    public void sendRequest(RequestDto requestDto) {
        String destination = "/request/" + requestDto.getAcceptUserId();
        simpMessagingTemplate.convertAndSend(destination, requestDto);
    }

    @RequestMapping("/getRequests")
    @ResponseBody
    public ResponseDto<List<RequestDto>> getRequests(HttpSession session) {
        ResponseDto<List<RequestDto>> responseDto = new ResponseDto<List<RequestDto>>();
        try {
            AccountDto accountDto = (AccountDto) session.getAttribute("s_user");
            if (accountDto != null) {
                List<RequestDto> requestDtoList = requestService.getRequestsById(accountDto.getId());
                responseDto.setContent(requestDtoList);
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
            if (isUserLegal(userId, session)) {
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
            if (isUserLegal(userId, session)) {
                int requestUserId = requestService.acceptRequest(requestId);
                responseDto.setContent(true);

                AccountDto contactAccountDto = accountService.getSimpleAccountById(requestUserId);
                AccountDto accountDto = (AccountDto) session.getAttribute("s_user");
                accountDto.getContacts().add(contactAccountDto);
                session.setAttribute("s_user", accountDto);
            } else {
                responseDto.setContent(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setCode(500);
        }
        return responseDto;
    }
}
