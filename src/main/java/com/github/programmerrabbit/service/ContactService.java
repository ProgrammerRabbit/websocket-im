package com.github.programmerrabbit.service;

import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.dto.ContactDto;

import java.util.Set;

/**
 * Created by Rabbit on 2016/12/17.
 */
public interface ContactService {
    Set<AccountDto> getContacts(int userId) throws Exception;

    Set<AccountDto> getContactsWithCache(int userId) throws Exception;

    void addContactPair(ContactDto contactDto) throws Exception;
}
