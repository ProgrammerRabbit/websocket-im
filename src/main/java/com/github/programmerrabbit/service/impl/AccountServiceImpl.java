package com.github.programmerrabbit.service.impl;

import com.github.programmerrabbit.dao.AccountDao;
import com.github.programmerrabbit.dao.entity.Account;
import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.service.AccountService;
import com.github.programmerrabbit.service.ContactService;
import com.github.programmerrabbit.utils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;

    @Resource
    private ContactService contactService;

    public AccountDto login(AccountDto accountDto) throws Exception {
        AccountDto dbAccountDto = getSimpleAccountByUsername(accountDto.getUsername());
        if (dbAccountDto != null && accountDto.getPassword().equals(accountDto.getPassword())) {
            Set<AccountDto> contacts = contactService.getContactsWithCache(dbAccountDto.getId());
            dbAccountDto.setContacts(contacts);
            return dbAccountDto;
        }
        return null;
    }

    public AccountDto getSimpleAccountByUsername(String username) throws Exception {
        List<Account> accountList = accountDao.getByField("username", username);
        if (accountList.size() == 1) {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(accountList.get(0), accountDto);
            return accountDto;
        }
        return null;
    }

    public AccountDto getSimpleAccountById(int id) throws Exception {
        List<Account> accountList = accountDao.getByField("id", String.valueOf(id));
        if (accountList.size() == 1) {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(accountList.get(0), accountDto);
            return accountDto;
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
