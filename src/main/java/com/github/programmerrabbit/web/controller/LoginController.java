package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.utils.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
            // 1 verifyCode
            String verifyCode = getVerifyCodeFromSession(session);
            if (verifyCode == null || !verifyCode.equalsIgnoreCase(accountDto.getVerifyCode())) {
                Map<String, Object> model = MapUtils.newHashMap();

                model.put("errorHint", "VERIFY CODE error!<br><br>");

                return newModelAndView("login", model);
            }

            // 2 validate username and password
            AccountDto dbAccount = accountService.login(accountDto);
            if (dbAccount == null) {
                Map<String, Object> model = MapUtils.newHashMap();

                model.put("errorHint", "USERNAME or PASSWORD error!<br><br>");

                return newModelAndView("login", model);
            }

            // valid
            putLoginAccount2Session(session, dbAccount);

            return new ModelAndView(new RedirectView("/index"));
        } catch (Exception e) {
            e.printStackTrace();

            return newModelAndView("error");
        }
    }
}
