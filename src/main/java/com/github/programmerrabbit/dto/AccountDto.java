package com.github.programmerrabbit.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rabbit on 2016/12/15.
 */
@Data
public class AccountDto implements Serializable {
    private int id;
    private String username;
    private String password;
    private String verifyCode;

    private List<AccountDto> friedList;
}
