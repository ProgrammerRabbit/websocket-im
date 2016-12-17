package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.RequestDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.service.RequestService;
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

    @RequestMapping("/addContact")
    @ResponseBody
    public ResponseDto<Boolean> addContact(String username, HttpServletRequest request, HttpServletResponse response) {
        ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();
        try {
            if (accountService.isUsernameRegistered(username)) {
                AccountDto accountDto = (AccountDto) request.getSession().getAttribute("s_user");
                AccountDto contactAccountDto = accountService.getSimpleAccountByUsername(username);
                if (accountDto.getContacts().contains(contactAccountDto)) {
                    responseDto.setContent(false);
                    responseDto.setMessage("\"" + username + "\" is already your contact!");
                } else {
                    responseDto.setContent(true);
                    RequestDto requestDto = new RequestDto();
                    requestDto.setRequestUserId(accountDto.getId());
                    requestDto.setRequestUserName(accountDto.getUsername());
                    requestDto.setAcceptUserId(contactAccountDto.getId());
                    requestService.addRequest(requestDto);
                    request.getRequestDispatcher("/sendRequest").forward(request, response);
                }
            } else {
                responseDto.setContent(false);
                responseDto.setMessage("\"" + username + "\" is not exist!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setCode(500);
            responseDto.setMessage("Sorry, some error occurred!");
        }
        return responseDto;
    }
}
