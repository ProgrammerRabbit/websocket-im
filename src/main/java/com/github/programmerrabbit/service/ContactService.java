package com.github.programmerrabbit.service;

import com.github.programmerrabbit.dto.AccountDto;

import java.util.Set;

/**
 * Created by Rabbit on 2016/12/17.
 */
public interface ContactService {
    Set<AccountDto> getContacts(int id) throws Exception;

    Set<AccountDto> getContactsWithCache(int id) throws Exception;
}
