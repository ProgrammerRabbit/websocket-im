package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Controller
public class LoginController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/login")
    public String login(AccountDto accountDto, HttpServletRequest request) {
        try {
            String code = (String) request.getSession().getAttribute("s_code");
            if (code == null || !code.equalsIgnoreCase(accountDto.getVerifyCode())) {
                request.setAttribute("r_error", "VERIFY CODE error!<br><br>");
                return "forward:/index/login.jsp";
            }

            AccountDto dbAccountDto = accountService.getAccount(accountDto);
            if (dbAccountDto == null) {
                request.setAttribute("r_error", "USERNAME or PASSWORD error!<br><br>");
                return "forward:/index/login.jsp";
            } else {
                request.getSession().setAttribute("s_user", dbAccountDto);
                return "redirect:/resources/chat.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index/error.html";
        }
    }
}
