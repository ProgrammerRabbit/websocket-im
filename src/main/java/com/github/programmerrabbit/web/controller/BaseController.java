package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rabbit on 2016/12/16.
 */
public class BaseController {
    private final static String SESSION_ATTR_USER = "s_user";
    private final static String SESSION_ATTR_VERIFY_CODE = "s_code";
    
    protected ModelAndView newModelAndView(String view) {
        return newModelAndView(view, new HashMap<String, Object>());
    }

    protected ModelAndView newModelAndView(String view, Map<String, Object> map) {
        ModelAndView modelAndView = new ModelAndView();
        for (String key : map.keySet()) {
            modelAndView.addObject(key, map.get(key));
        }
        modelAndView.setViewName(view);
        return modelAndView;
    }
    
    protected void putLoginAccount2Session(HttpSession session, AccountDto loginAccount) {
        session.setAttribute(SESSION_ATTR_USER, loginAccount);
    }

    protected AccountDto getLoginAccountFromSession(HttpSession session) {
        return (AccountDto) session.getAttribute(SESSION_ATTR_USER);
    }
    
    protected void removeLoginAccountFromSession(HttpSession session) {
        session.removeAttribute(SESSION_ATTR_USER);
    }

    protected void putVerifyCode2Session(HttpSession session, String verifyCode) {
        session.setAttribute(SESSION_ATTR_VERIFY_CODE, verifyCode);
    }

    protected String getVerifyCodeFromSession(HttpSession session) {
        return (String) session.getAttribute(SESSION_ATTR_VERIFY_CODE);
    }
    
    protected boolean isUserLegal(int userId, HttpSession session) {
        AccountDto accountDto = getLoginAccountFromSession(session);
        return accountDto.getId() == userId;
    }
}
