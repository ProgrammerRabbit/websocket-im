package com.github.programmerrabbit.dao;

import com.github.programmerrabbit.BaseTest;
import com.github.programmerrabbit.entity.Account;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rabbit on 2016/12/12.
 */
public class AccountDaoTest extends BaseTest {
    @Resource
    private AccountDao accountDao;

    @Test
    public void insert() throws Exception {
        Account account = new Account();
        account.setUsername("rabbit");
        account.setPassword("rabbit");
        accountDao.insert(account);
    }

    @Test
    public void getById() throws Exception {
        Account account = accountDao.getById(5);
        System.out.println(account.toString());
    }

    @Test
    public void getByField() throws Exception {
        List<Account> accountList = accountDao.getByField("useranem", "rabbit");
        for (Account account : accountList) {
            System.out.println(account.toString());
        }
    }

    @Test
    public void updateById() throws Exception {
        Account account = new Account();
        account.setUsername("rabbit");
        account.setPassword("tibbar");
        accountDao.updateById(5, account);
    }

    @Test
    public void deleteById() throws Exception {
        accountDao.deleteById(5);
    }

}