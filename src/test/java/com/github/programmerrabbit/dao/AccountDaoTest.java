package com.github.programmerrabbit.dao;

import com.github.programmerrabbit.BaseTest;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * Created by Rabbit on 2016/12/12.
 */
public class AccountDaoTest extends BaseTest {
    @Resource
    private AccountDao accountDao;

    @Test
    public void getAccountByUsername() throws Exception {
        Assert.isNull(accountDao.getAccountByUsername("rabbit"));
    }

}