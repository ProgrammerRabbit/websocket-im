package com.github.programmerrabbit.dao;

import com.github.programmerrabbit.entity.Account;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Rabbit on 2016/12/12.
 */
public interface AccountDao {
    Account getAccountByUsername(@Param("username") String username);
}
