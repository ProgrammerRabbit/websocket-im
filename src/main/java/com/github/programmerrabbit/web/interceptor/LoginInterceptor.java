package com.github.programmerrabbit.web.interceptor;

import com.github.programmerrabbit.dto.AccountDto;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Rabbit on 2016/12/15.
 */
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AccountDto accountDto = (AccountDto) request.getSession().getAttribute("s_user");
        if (accountDto == null) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("errorHint", "Login, Please!<br><br>");
            modelAndView.setViewName("login");
            throw new ModelAndViewDefiningException(modelAndView);
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
