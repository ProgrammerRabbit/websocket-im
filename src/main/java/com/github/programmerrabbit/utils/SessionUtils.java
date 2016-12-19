package com.github.programmerrabbit.utils;

import com.github.programmerrabbit.dto.AccountDto;

import javax.servlet.http.HttpSession;

/**
 * Created by Rabbit on 2016/12/19.
 */
public class SessionUtils {
    private final static String SESSION_ATTR_USER = "s_user";
    private final static String SESSION_ATTR_VERIFY_CODE = "s_code";

    public static void putLoginAccount(HttpSession session, AccountDto loginAccount) {
        session.setAttribute(SESSION_ATTR_USER, loginAccount);
    }

    public static AccountDto getLoginAccount(HttpSession session) {
        return (AccountDto) session.getAttribute(SESSION_ATTR_USER);
    }

    public static void removeLoginAccount(HttpSession session) {
        session.removeAttribute(SESSION_ATTR_USER);
    }

    public static void putVerifyCode(HttpSession session, String verifyCode) {
        session.setAttribute(SESSION_ATTR_VERIFY_CODE, verifyCode);
    }

    public static String getVerifyCode(HttpSession session) {
        return (String) session.getAttribute(SESSION_ATTR_VERIFY_CODE);
    }

    public static boolean isUserLegal(int userId, HttpSession session) {
        AccountDto accountDto = getLoginAccount(session);
        return accountDto.getId() == userId;
    }
}
