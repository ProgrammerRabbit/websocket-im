package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.utils.MapUtils;
import com.github.programmerrabbit.utils.ModelAndViewUtils;
import com.github.programmerrabbit.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Rabbit on 2016/12/16.
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public ModelAndView index(HttpSession session) {
        try {
            Map<String, Object> model = MapUtils.newHashMap();

            AccountDto loginAccount = SessionUtils.getLoginAccount(session);
            model.put("user", loginAccount);

            return ModelAndViewUtils.newInstance("index", model);
        } catch (Exception e) {
            e.printStackTrace();

            return ModelAndViewUtils.newInstance("error");
        }
    }
}
