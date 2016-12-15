package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Controller
public class LoginController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/login")
    public String login(AccountDto accountDto, HttpSession httpSession) {
        try {
            AccountDto dbAccountDto = accountService.getAccount(accountDto);
            if (dbAccountDto == null) {
                return "redirect:/resources/login.html";
            } else {
                httpSession.setAttribute("s_user", dbAccountDto);
                return "redirect:/resources/page/chat.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/resources/login.html";
        }
    }
}