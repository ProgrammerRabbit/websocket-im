package com.github.programmerrabbit.enums;

/**
 * Created by Rabbit on 2016/12/18.
 */
public enum MessageStatusEnum {
    SENT(0),
    READ(1),
    ALL(2);

    private int code;

    MessageStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
