package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Controller
public class RegisterController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/register")
    public String register(AccountDto accountDto, HttpServletRequest request) {
        try {
            String code = (String) request.getSession().getAttribute("s_code");
            if (code == null || !code.equalsIgnoreCase(accountDto.getVerifyCode())) {
                request.setAttribute("r_error", "VERIFY CODE error!<br><br>");
                return "forward:/index/register.jsp";
            }

            if (accountService.isUsernameRegistered(accountDto.getUsername())) {
                request.setAttribute("r_error", "USERNAME is already registered!<br><br>");
                return "forward:/index/register.jsp";
            } else {
                accountService.addAccount(accountDto);
                return "redirect:/index/login.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index/error.html";
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
