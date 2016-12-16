package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Controller
@RequestMapping("/visit")
public class RegisterController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping("/doRegister")
    public ModelAndView register(AccountDto accountDto, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String code = (String) session.getAttribute("s_code");
            if (code == null || !code.equalsIgnoreCase(accountDto.getVerifyCode())) {
                modelAndView.addObject("errorHint", "VERIFY CODE error!<br><br>");
                modelAndView.setViewName("register");
                return modelAndView;
            }

            if (accountService.isUsernameRegistered(accountDto.getUsername())) {
                modelAndView.addObject("errorHint", "USERNAME is already registered!<br><br>");
                modelAndView.setViewName("register");
                return modelAndView;
            } else {
                accountService.addAccount(accountDto);
                modelAndView.setViewName("login");
                return modelAndView;
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping("/isUsernameRegistered")
    @ResponseBody
    public ResponseDto<Boolean> isUsernameRegistered(String username) {
        ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();
        try {
            responseDto.setContent(accountService.isUsernameRegistered(username));
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setCode(500);
        }
        return responseDto;
    }
}
