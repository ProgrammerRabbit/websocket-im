package com.github.programmerrabbit.web.controller;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.RequestDto;
import com.github.programmerrabbit.dto.ResponseDto;
import com.github.programmerrabbit.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Controller
public class RequestController extends BaseController {
    @Resource
    private RequestService requestService;

    @RequestMapping("/getRequests")
    @ResponseBody
    public ResponseDto<List<RequestDto>> getRequests(HttpSession session) {
        ResponseDto<List<RequestDto>> responseDto = new ResponseDto<List<RequestDto>>();
        try {
            AccountDto accountDto = (AccountDto) session.getAttribute("s_user");
            if (accountDto != null) {
                List<RequestDto> requestDtoList = requestService.getRequestsById(accountDto.getId());
                responseDto.setContent(requestDtoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setCode(500);
        }
        return responseDto;
    }

    @RequestMapping("/rejectRequest")
    @ResponseBody
    public void rejectRequest(int requestId, int userId, HttpSession session) {
        try {
            if (isUserLegal(userId, session)) {
                requestService.rejectRequest(requestId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/acceptRequest")
    @ResponseBody
    public void acceptRequest(int requestId, int userId, HttpSession session) {
        try {
            if (isUserLegal(userId, session)) {
                requestService.acceptRequest(requestId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
