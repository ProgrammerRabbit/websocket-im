package com.github.programmerrabbit.service.impl;

import com.github.programmerrabbit.dao.RequestDao;
import com.github.programmerrabbit.dao.entity.Request;
import com.github.programmerrabbit.dto.ContactDto;
import com.github.programmerrabbit.dto.RequestDto;
import com.github.programmerrabbit.service.ContactService;
import com.github.programmerrabbit.service.RequestService;
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

    public List<RequestDto> getRequestsByUserId(int userId) throws Exception {
        List<Request> requestList = requestDao.getByField("acceptUserId", String.valueOf(userId));
        List<RequestDto> requestDtoList = CollectionUtils.newArrayList();
        for (Request request : requestList) {
            requestDtoList.add(RequestDto.fromEntity(request));
        }
        return requestDtoList;
    }

    public void rejectRequest(int requestId) throws Exception {
        requestDao.deleteById(requestId);
    }

    public RequestDto acceptRequest(int requestId) throws Exception {
        // add contact pair
        Request request = requestDao.getById(requestId);
        ContactDto contactPair = new ContactDto();
        contactPair.setOneUserId(request.getRequestUserId());
        contactPair.setAnotherUserId(request.getAcceptUserId());
        contactService.addContactPair(contactPair);

        // delete request
        requestDao.deleteById(requestId);

        // return request info
        return RequestDto.fromEntity(request);
    }

    public void persistRequest(RequestDto requestDto) throws Exception {
        Request request = requestDto.toEntity();
        requestDao.insert(request);

        requestDto.setId(request.getId());
    }

    public boolean isRequestExist(int requestUserId, int acceptUserId) throws Exception {
        List<Request> requests = requestDao.getRequestByRequestAndAcceptUserId(requestUserId, acceptUserId);
        return requests.size() != 0;
    }

    public RequestDto getRequestById(int id) throws Exception {
        Request request = requestDao.getById(id);
        return RequestDto.fromEntity(request);
    }
}
