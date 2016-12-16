package com.github.programmerrabbit.dao.entity;

import com.github.programmerrabbit.DaoCodeGenerator;
import com.github.programmerrabbit.ProjectPath;
import lombok.Data;

/**
 * Created by Rabbit on 2016/12/17.
 */
@Data
public class Contact {
    private int id;
    private int oneUserId;
    private int anotherUserId;

    public static void main(String[] args) {
        ProjectPath projectPath = new ProjectPath();
        projectPath.setDaoRelativePath("../");
        DaoCodeGenerator.generateDaoCode(Contact.class, projectPath);
    }
}
