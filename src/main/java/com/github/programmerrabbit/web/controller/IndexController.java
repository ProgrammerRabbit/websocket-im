package com.github.programmerrabbit.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Rabbit on 2016/12/16.
 */
@Controller
public class IndexController extends BaseController {
    @RequestMapping("/index")
    public ModelAndView index() {
        return newModelAndView("index");
    }
}
