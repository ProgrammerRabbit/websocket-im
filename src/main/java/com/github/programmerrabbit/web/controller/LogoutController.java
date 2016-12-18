package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.utils.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Controller
public class LogoutController extends BaseController {
    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        removeLoginAccountFromSession(session);

        Map<String, Object> model = MapUtils.newHashMap();

        model.put("errorHint", "Logout succeed!<br><br>");

        return newModelAndView("login", model);
    }
}
