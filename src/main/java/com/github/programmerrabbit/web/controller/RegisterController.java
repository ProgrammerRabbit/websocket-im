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
    public ModelAndView register(AccountDto formAccount, HttpSession session) {
        try {
            String verifyCode = getVerifyCodeFromSession(session);

            // 1 verify code
            if (verifyCode == null || !verifyCode.equalsIgnoreCase(formAccount.getVerifyCode())) {
                Map<String, Object> model = MapUtils.newHashMap();

                model.put("errorHint", "VERIFY CODE error!<br><br>");

                return newModelAndView("register", model);
            }

            // 2 special character
            if (formAccount.getUsername().contains(" ") || formAccount.getUsername().contains("<")) {
                Map<String, Object> model = MapUtils.newHashMap();

                model.put("errorHint", "USERNAME shouldn't contains whitespace or '&lt;'!<br><br>");

                return newModelAndView("register", model);
            }

            // 3 empty username
            if (StringUtils.isNullOrEmptyWithTrim(formAccount.getUsername())) {
                Map<String, Object> model = MapUtils.newHashMap();

                model.put("errorHint", "USERNAME shouldn't be empty!");

                return newModelAndView("register", model);
            }

            // 4 username length
            if (formAccount.getUsername().length() > 20) {
                Map<String, Object> model = MapUtils.newHashMap();

                model.put("errorHint", "USERNAME should be 1-20 long!");

                return newModelAndView("register", model);
            }

            // 5 empty password
            if (StringUtils.isNullOrEmptyWithTrim(formAccount.getPassword())) {
                Map<String, Object> model = MapUtils.newHashMap();

                model.put("errorHint", "PASSWORD shouldn't be empty");

                return newModelAndView("register", model);
            }

            // 6 username registered
            if (accountService.isUsernameRegistered(formAccount.getUsername())) {
                Map<String, Object> model = MapUtils.newHashMap();

                model.put("errorHint", "USERNAME is already registered!<br><br>");

                return newModelAndView("register", model);
            }

            // valid
            accountService.addAccount(formAccount);

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
