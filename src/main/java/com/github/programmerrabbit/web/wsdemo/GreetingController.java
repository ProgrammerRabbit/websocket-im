package com.github.programmerrabbit.web.wsdemo;

import com.github.programmerrabbit.test.Demo;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by Rabbit on 2016/12/13.
 */
@Demo
@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage helloMessage) throws Exception {
        Thread.sleep(3000);
        return new Greeting("Hello, " + helloMessage.getName() + "!");
    }
}
