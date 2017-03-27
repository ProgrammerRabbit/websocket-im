package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.utils.MapUtils;
import com.github.programmerrabbit.utils.ModelAndViewUtils;
import com.github.programmerrabbit.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Controller
public class LogoutController {
    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        SessionUtils.removeLoginAccount(session);

        Map<String, Object> model = MapUtils.newHashMap();

        model.put("errorHint", "Logout succeed!<br><br>");

        return ModelAndViewUtils.newInstance("login", model);
    }
}
