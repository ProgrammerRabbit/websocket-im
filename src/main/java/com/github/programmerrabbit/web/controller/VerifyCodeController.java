package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.utils.SessionUtils;
import com.github.programmerrabbit.verifycode.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Controller
@RequestMapping("/visit")
public class VerifyCodeController {
    @RequestMapping("/verifyCode")
    private void generateVerifyCode(HttpSession session, HttpServletResponse response) {
        try {
            VerifyCode verifyCode = new VerifyCode();
            BufferedImage image = verifyCode.getVerifyCodeImage();
            SessionUtils.putVerifyCode(session, verifyCode.getVerifyCodeText());
            verifyCode.writeOutputStream(image, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
