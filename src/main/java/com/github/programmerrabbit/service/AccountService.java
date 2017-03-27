package com.github.programmerrabbit.service;

import com.github.programmerrabbit.dto.AccountDto;

/**
 * Created by Rabbit on 2016/12/15.
 */
public interface AccountService {
    AccountDto login(AccountDto accountDto) throws Exception;

    AccountDto getSimpleAccountByUsername(String username) throws Exception;

    AccountDto getSimpleAccountById(int id) throws Exception;

    void addAccount(AccountDto accountDto) throws Exception;

    boolean isUsernameRegistered(String username) throws Exception;
}
