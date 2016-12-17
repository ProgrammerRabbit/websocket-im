package com.github.programmerrabbit.service.impl;

import com.github.programmerrabbit.dao.RequestDao;
import com.github.programmerrabbit.dao.entity.Request;
import com.github.programmerrabbit.dto.ContactDto;
import com.github.programmerrabbit.dto.RequestDto;
import com.github.programmerrabbit.service.ContactService;
import com.github.programmerrabbit.service.RequestService;
import com.github.programmerrabbit.utils.BeanUtils;
import com.github.programmerrabbit.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Service
public class RequestServiceImpl implements RequestService {
    @Resource
    private RequestDao requestDao;

    @Resource
    private ContactService contactService;

    public List<RequestDto> getRequestsById(int id) throws Exception {
        List<Request> requestList = requestDao.getByField("acceptUserId", String.valueOf(id));
        List<RequestDto> requestDtoList = CollectionUtils.newArrayList();
        for (Request request : requestList) {
            RequestDto requestDto = new RequestDto();
            BeanUtils.copyProperties(request, requestDto);
            requestDtoList.add(requestDto);
        }
        return requestDtoList;
    }

    public void rejectRequest(int requestId) throws Exception {
        requestDao.deleteById(requestId);
    }

    public void acceptRequest(int requestId) throws Exception {
        requestDao.deleteById(requestId);
        Request request = requestDao.getById(requestId);
        ContactDto contactPair = new ContactDto();
        contactPair.setOneUserId(request.getRequestUserId());
        contactPair.setAnotherUserId(request.getAcceptUserId());
        contactService.addContactPair(contactPair);
    }

    public void addRequest(RequestDto requestDto) throws Exception {
        Request request = new Request();
        BeanUtils.copyProperties(requestDto, request);
        requestDao.insert(request);
    }
}
