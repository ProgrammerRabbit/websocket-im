package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.RequestDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.service.MessageService;
import com.github.programmerrabbit.service.RequestService;
import com.github.programmerrabbit.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Controller
public class ContactController {
    @Resource
    private AccountService accountService;

    @Resource
    private RequestService requestService;

    @Resource
    private MessageService messageService;

    @RequestMapping("/addContact")
    @ResponseBody
    public ResponseDto<Boolean> addContact(String username, HttpServletRequest request, HttpServletResponse response) {
        ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();

        try {
            AccountDto loginAccount = SessionUtils.getLoginAccount(request.getSession());
            AccountDto contact = accountService.getSimpleAccountByUsername(username);

            // 1 username not registered
            if (!accountService.isUsernameRegistered(username)) {
                responseDto.setMessage("\"" + username + "\" is not exist!");
                responseDto.setContent(false);

            }
            // 2 already contact
            else if (loginAccount.getContacts().contains(contact)) {
                responseDto.setMessage("\"" + username + "\" is already your contact!");
                responseDto.setContent(false);
            }
            // 3 self
            else if (loginAccount.getId() == contact.getId()) {
                responseDto.setMessage("You can't add yourself as your contact!");
                responseDto.setContent(false);
            }
            // valid
            else {
                if (!requestService.isRequestExist(loginAccount.getId(), contact.getId())) {
                    RequestDto newRequest = buildRequest(loginAccount, contact);
                    requestService.persistRequest(newRequest);

                    String destination = "/topic/request/" + newRequest.getAcceptUserId();
                    messageService.sendWebSocketMessage(destination, newRequest);
                }
                responseDto.setContent(true);
            }
        } catch (Exception e) {
            e.printStackTrace();

            responseDto.setMessage("Sorry, some error occurred!");
            responseDto.setCode(500);
        }

        return responseDto;
    }

    private RequestDto buildRequest(AccountDto loginAccount, AccountDto contact) {
        RequestDto request = new RequestDto();

        request.setRequestUserId(loginAccount.getId());
        request.setRequestUserName(loginAccount.getUsername());
        request.setAcceptUserId(contact.getId());

        return request;
    }
}
