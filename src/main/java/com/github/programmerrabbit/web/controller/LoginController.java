package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.utils.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Controller
@RequestMapping("/visit")
public class LoginController extends BaseController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/login")
    public ModelAndView login() {
        return newModelAndView("login");
    }

    @RequestMapping(path = "/doLogin", method = RequestMethod.POST)
    public ModelAndView login(AccountDto accountDto, HttpSession session) {
        try {
            String code = (String) session.getAttribute("s_code");
            if (code == null || !code.equalsIgnoreCase(accountDto.getVerifyCode())) {
                Map<String, Object> map = MapUtils.newHashMap();
                map.put("errorHint", "VERIFY CODE error!<br><br>");
                return newModelAndView("login", map);
            }

            AccountDto dbAccountDto = accountService.getAccount(accountDto);
            if (dbAccountDto == null) {
                Map<String, Object> map = MapUtils.newHashMap();
                map.put("errorHint", "USERNAME or PASSWORD error!<br><br>");
                return newModelAndView("login", map);
            }

            session.setAttribute("s_user", dbAccountDto);
            return newModelAndView("index");
        } catch (Exception e) {
            e.printStackTrace();
            return newModelAndView("error");
        }
    }
}
