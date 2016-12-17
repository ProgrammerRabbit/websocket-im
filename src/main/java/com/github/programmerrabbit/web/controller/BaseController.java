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

    protected boolean isUserLegal(int userId, HttpSession session) {
        AccountDto accountDto = (AccountDto) session.getAttribute("s_user");
        return accountDto.getId() == userId;
    }
}
