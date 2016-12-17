package com.github.programmerrabbit.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Data
public class ContactDto implements Serializable {
    private int id;
    private int oneUserId;
    private int anotherUserId;
}
