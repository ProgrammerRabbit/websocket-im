package com.github.programmerrabbit.web.wsdemo;

import com.github.programmerrabbit.test.Demo;

/**
 * Created by Rabbit on 2016/12/13.
 */
@Demo
public class Greeting {
    private String content;

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
