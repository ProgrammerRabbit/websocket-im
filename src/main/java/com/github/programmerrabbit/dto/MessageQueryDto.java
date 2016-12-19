package com.github.programmerrabbit.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rabbit on 2016/12/19.
 */
@Data
public class MessageQueryDto implements Serializable {
    private int id;
    private Date addTimeFrom;
    private Date addTimeTo;
    private int fromId;
    private int toId;
    private int status;

    private boolean bothSide; // make fromId and toId as a pair

    private int sortType;
}
