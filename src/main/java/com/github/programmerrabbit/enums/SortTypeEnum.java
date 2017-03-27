package com.github.programmerrabbit.enums;

/**
 * Created by Rabbit on 2016/12/19.
 */
public enum SortTypeEnum {
    ASC(0),
    DESC(1);

    private int code;

    SortTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
