package com.github.programmerrabbit.dto;

import com.github.programmerrabbit.dao.entity.Account;
import com.github.programmerrabbit.utils.BeanUtils;
import lombok.Data;

import javax.swing.text.html.parser.Entity;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Data
public class AccountDto implements Serializable {
    private int id;
    private String username;
    private String password;
    private String verifyCode;

    private Set<AccountDto> contacts;

    public Account toEntity() {
        Account account = new Account();
        BeanUtils.copyProperties(this, account);
        return account;
    }

    public static AccountDto fromEntity(Account account) {
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);
        return accountDto;
    }
}
