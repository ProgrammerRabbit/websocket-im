package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.utils.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Rabbit on 2016/12/16.
 */
@Controller
public class IndexController extends BaseController {
    @RequestMapping("/index")
    public ModelAndView index(HttpSession session) {
        AccountDto accountDto = (AccountDto) session.getAttribute("s_user");
        Map<String, Object> map = MapUtils.newHashMap();
        map.put("user", accountDto);
        return newModelAndView("index", map);
    }
}
