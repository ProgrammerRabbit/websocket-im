package com.github.programmerrabbit.web.interceptor;

import com.github.programmerrabbit.dto.AccountDto;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Rabbit on 2016/12/15.
 */
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AccountDto accountDto = (AccountDto) request.getSession().getAttribute("s_user");
        if (accountDto == null) {
            request.setAttribute("s_error", "Login, Please!");
            request.getRequestDispatcher("/index/login.jsp").forward(request, response);
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
