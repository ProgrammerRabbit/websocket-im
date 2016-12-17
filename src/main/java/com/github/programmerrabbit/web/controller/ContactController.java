package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Controller
public class ContactController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/addContact")
    @ResponseBody
    public ResponseDto<Boolean> addContact(String username, HttpSession session) {
        ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();
        try {
            if (accountService.isUsernameRegistered(username)) {
                AccountDto accountDto = (AccountDto) session.getAttribute("s_user");
                AccountDto contactAccountDto = accountService.getSimpleAccountByUsername(username);
                if (accountDto.getContacts().contains(contactAccountDto)) {
                    responseDto.setContent(false);
                    responseDto.setMessage("\"" + username + "\" is already your contact!");
                } else {
                    responseDto.setContent(true);
                    // TODO add contact
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
