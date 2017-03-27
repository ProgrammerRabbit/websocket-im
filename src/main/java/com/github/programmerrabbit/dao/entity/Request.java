package com.github.programmerrabbit.dao.entity;

import com.github.programmerrabbit.DaoCodeGenerator;
import com.github.programmerrabbit.ProjectPath;
import lombok.Data;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Data
public class Request {
    private int id;
    private int requestUserId;
    private String requestUserName;
    private int acceptUserId;

    public static void main(String[] args) {
        ProjectPath projectPath = new ProjectPath();
        projectPath.setDaoRelativePath("../");
        DaoCodeGenerator.generateDaoCode(Request.class, projectPath);
    }
}
