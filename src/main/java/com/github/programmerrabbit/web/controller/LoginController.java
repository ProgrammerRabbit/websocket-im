package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Controller
@RequestMapping("/visit")
public class LoginController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/doLogin")
    public ModelAndView login(AccountDto accountDto, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String code = (String) session.getAttribute("s_code");
            if (code == null || !code.equalsIgnoreCase(accountDto.getVerifyCode())) {
                modelAndView.addObject("errorHint", "VERIFY CODE error!<br><br>");
                modelAndView.setViewName("login");
                return modelAndView;
            }

            AccountDto dbAccountDto = accountService.getAccount(accountDto);
            if (dbAccountDto == null) {
                modelAndView.addObject("errorHint", "USERNAME or PASSWORD error!<br><br>");
                modelAndView.setViewName("login");
                return modelAndView;
            } else {
                session.setAttribute("s_user", dbAccountDto);
                modelAndView.setViewName("index");
                return modelAndView;
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }
}
