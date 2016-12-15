package com.github.programmerrabbit.service;

import com.github.programmerrabbit.dto.AccountDto;

/**
 * Created by Rabbit on 2016/12/15.
 */
public interface AccountService {
    AccountDto getAccount(AccountDto accountDto) throws Exception;

    void addAccount(AccountDto accountDto) throws Exception;
}
