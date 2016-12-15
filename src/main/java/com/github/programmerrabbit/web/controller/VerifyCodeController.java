package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.verifycode.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Controller
public class VerifyCodeController {
    @RequestMapping("/verifyCode")
    private void generateVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            VerifyCode verifyCode = new VerifyCode();
            BufferedImage image = verifyCode.getVerifyCodeImage();
            request.getSession().setAttribute("s_code", verifyCode.getVerifyCodeText());
            verifyCode.writeOutputStream(image, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
