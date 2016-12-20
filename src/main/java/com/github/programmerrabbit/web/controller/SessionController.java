package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Rabbit on 2016/12/20.
 */
@Controller
public class SessionController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/updateSession")
    @ResponseBody
    public void updateSession(HttpSession session) {
        try {
            AccountDto loginAccount = SessionUtils.getLoginAccount(session);
            AccountDto fresherLoginAccount = accountService.login(loginAccount);
            SessionUtils.putLoginAccount(session, fresherLoginAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
