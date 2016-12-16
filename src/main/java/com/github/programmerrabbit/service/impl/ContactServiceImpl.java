package com.github.programmerrabbit.service.impl;

import com.github.programmerrabbit.cache.Cache;
import com.github.programmerrabbit.cache.ConcurrentCache;
import com.github.programmerrabbit.cache.ExpireTypeEnum;
import com.github.programmerrabbit.dao.AccountDao;
import com.github.programmerrabbit.dao.ContactDao;
import com.github.programmerrabbit.dao.entity.Account;
import com.github.programmerrabbit.dao.entity.Contact;
import com.github.programmerrabbit.dto.AccountDto;
import com.github.programmerrabbit.service.ContactService;
import com.github.programmerrabbit.utils.BeanUtils;
import com.github.programmerrabbit.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Service
public class ContactServiceImpl implements ContactService {
    @Resource
    private ContactDao contactDao;

    @Resource
    private AccountDao accountDao;

    private Cache<Integer, Set<AccountDto>> cache = new ConcurrentCache<Integer, Set<AccountDto>>(ExpireTypeEnum.EXPIRE_AFTER_WRITE, 5, TimeUnit.MINUTES);

    public Set<AccountDto> getContacts(int id) {
        List<Contact> anotherContactList = contactDao.getByField("oneUserId", id);
        List<Contact> oneContactList = contactDao.getByField("anotherUserId", id);
        Set<AccountDto> contacts = CollectionUtils.newHashSet();
        for (Contact anotherContact : anotherContactList) {
            Account account = accountDao.getById(anotherContact.getAnotherUserId());
            if (account != null) {
                AccountDto accountDto = new AccountDto();
                BeanUtils.copyProperties(account, accountDto);
                contacts.add(accountDto);
            }
        }
        for (Contact oneContact : oneContactList) {
            Account account = accountDao.getById(oneContact.getOneUserId());
            if (account != null) {
                AccountDto accountDto = new AccountDto();
                BeanUtils.copyProperties(account, accountDto);
                contacts.add(accountDto);
            }
        }
        return contacts;
    }

    public Set<AccountDto> getContactsWithCache(final int id) {
        return cache.get(id, new Callable<Set<AccountDto>>() {
            public Set<AccountDto> call() throws Exception {
                return getContacts(id);
            }
        });
    }
}