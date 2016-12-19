package com.github.programmerrabbit.utils;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rabbit on 2016/12/19.
 */
public class ModelAndViewUtils {
    public static ModelAndView newInstance(String view) {
        return newInstance(view, new HashMap<String, Object>());
    }

    public static ModelAndView newInstance(String view, Map<String, Object> map) {
        ModelAndView modelAndView = new ModelAndView();
        for (String key : map.keySet()) {
            modelAndView.addObject(key, map.get(key));
        }
        modelAndView.setViewName(view);
        return modelAndView;
    }
}
