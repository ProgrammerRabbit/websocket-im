package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.utils.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Rabbit on 2016/12/16.
 */
@Controller
public class IndexController extends BaseController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/index")
    public ModelAndView index(HttpSession session) {
        try {
            Map<String, Object> model = MapUtils.newHashMap();

            AccountDto loginAccount = getLoginAccountFromSession(session);
            AccountDto fresherLoginAccount = accountService.login(loginAccount);
            model.put("user", fresherLoginAccount);

            return newModelAndView("index", model);
        } catch (Exception e) {
            e.printStackTrace();

            return newModelAndView("error");
        }
    }
}
