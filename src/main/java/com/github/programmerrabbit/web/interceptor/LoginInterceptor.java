package com.github.programmerrabbit.web.interceptor;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.utils.MapUtils;
import com.github.programmerrabbit.utils.ModelAndViewUtils;
import com.github.programmerrabbit.utils.SessionUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Rabbit on 2016/12/15.
 */
public class LoginInterceptor implements HandlerInterceptor, HandshakeInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AccountDto loginAccount = SessionUtils.getLoginAccount(request.getSession());
        if (loginAccount == null) {
            Map<String, Object> model = MapUtils.newHashMap();

            model.put("errorHint", "Login, Please!<br><br>");

            throw new ModelAndViewDefiningException(ModelAndViewUtils.newInstance("login", model));
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }



    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpSession session = ((ServletServerHttpRequest) request).getServletRequest().getSession();
            AccountDto loginAccount = SessionUtils.getLoginAccount(session);
            if (loginAccount == null) {
                Map<String, Object> model = MapUtils.newHashMap();

                model.put("errorHint", "Login, Please!<br><br>");

                throw new ModelAndViewDefiningException(ModelAndViewUtils.newInstance("login", model));
            }
        }
        return true;
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
