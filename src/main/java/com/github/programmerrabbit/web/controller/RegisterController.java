package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.utils.MapUtils;
import com.github.programmerrabbit.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Controller
@RequestMapping("/visit")
public class RegisterController extends BaseController {
    @Resource
    private AccountService accountService;

    @RequestMapping("/register")
    public ModelAndView register() {
        return newModelAndView("register");
    }

    @RequestMapping(path = "/doRegister", method = RequestMethod.POST)
    public ModelAndView register(AccountDto accountDto, HttpSession session) {
        try {
            String code = (String) session.getAttribute("s_code");

            // verify code
            if (code == null || !code.equalsIgnoreCase(accountDto.getVerifyCode())) {
                Map<String, Object> map = MapUtils.newHashMap();
                map.put("errorHint", "VERIFY CODE error!<br><br>");
                return newModelAndView("register", map);
            }

            // username not contains special character - whitespace, <
            if (accountDto.getUsername().contains(" ") || accountDto.getUsername().contains("<")) {
                Map<String, Object> map = MapUtils.newHashMap();
                map.put("errorHint", "USERNAME shouldn't contains whitespace or '&lt;'!<br><br>");
                return newModelAndView("register", map);
            }

            // username not null and not empty with trim
            if (StringUtils.isNullOrEmptyWithTrim(accountDto.getUsername())) {
                Map<String, Object> map = MapUtils.newHashMap();
                map.put("errorHint", "USERNAME shouldn't be empty!");
                return newModelAndView("register", map);
            }

            // username length between [1, 20]
            if (accountDto.getUsername().length() > 20) {
                Map<String, Object> map = MapUtils.newHashMap();
                map.put("errorHint", "USERNAME should be 1-20 long!");
                return newModelAndView("register", map);
            }

            // password not null and not empty with trim
            if (StringUtils.isNullOrEmptyWithTrim(accountDto.getPassword())) {
                Map<String, Object> map = MapUtils.newHashMap();
                map.put("errorHint", "PASSWORD shouldn't be empty");
                return newModelAndView("register", map);
            }

            // username not registered
            if (accountService.isUsernameRegistered(accountDto.getUsername())) {
                Map<String, Object> map = MapUtils.newHashMap();
                map.put("errorHint", "USERNAME is already registered!<br><br>");
                return newModelAndView("register");
            }

            accountService.addAccount(accountDto);
            return newModelAndView("login");
        } catch (Exception e) {
            e.printStackTrace();
            return newModelAndView("error");
        }
    }

    @RequestMapping("/isUsernameRegistered")
    @ResponseBody
    public ResponseDto<Boolean> isUsernameRegistered(String username) {
        ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();
        try {
            responseDto.setContent(accountService.isUsernameRegistered(username));
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setCode(500);
        }
        return responseDto;
    }
}
