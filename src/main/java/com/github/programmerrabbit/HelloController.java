package com.github.programmerrabbit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Rabbit on 2016/12/11.
 */
@Controller
public class HelloController {
    @RequestMapping("hello")
    @ResponseBody
    public String sayHello() {
        return "Hello World";
    }
}
