package com.github.programmerrabbit.service.impl;

import com.github.programmerrabbit.dao.AccountDao;
import com.github.programmerrabbit.dao.entity.Account;
import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.utils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;

    public AccountDto getAccount(AccountDto accountDto) throws Exception {
        List<Account> accountList = accountDao.getByField("username", accountDto.getUsername());
        if (accountList.size() == 1 && accountList.get(0).getPassword().equals(accountDto.getPassword())) {
            AccountDto dbAccountDto = new AccountDto();
            BeanUtils.copyProperties(accountList.get(0), dbAccountDto);
            return dbAccountDto;
        }
        return null;
    }

    public void addAccount(AccountDto accountDto) throws Exception {
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        accountDao.insert(account);
    }

    public boolean isUsernameRegistered(String username) throws Exception {
        List<Account> accountList = accountDao.getByField("username", username);
        return accountList.size() != 0;
    }
}
