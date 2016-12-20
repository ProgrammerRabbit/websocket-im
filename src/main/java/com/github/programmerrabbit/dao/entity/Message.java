package com.github.programmerrabbit.dao.entity;

import com.github.programmerrabbit.DaoCodeGenerator;
import com.github.programmerrabbit.ProjectPath;
import lombok.Data;

import java.util.Date;

/**
 * Created by Rabbit on 2016/12/18.
 */
@Data
public class Message {
    private int id;
    private Date addTime;
    private int fromId;
    private String fromUserName;
    private int toId;
    private String content;
    private int status;

    public static void main(String[] args) {
        ProjectPath projectPath = new ProjectPath();
        projectPath.setDaoRelativePath("../");
        DaoCodeGenerator.generateDaoCode(Message.class, projectPath);
    }
}
