package com.github.programmerrabbit.dao;

import com.github.programmerrabbit.BaseTest;
import com.github.programmerrabbit.dao.entity.Message;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Generated by Code-Generator.
 * Author: ProgrammerRabbit
 * http://github.com/ProgrammerRabbit
 */
public class MessageDaoTest extends BaseTest {
    @Resource
    private MessageDao dao;

    private final static int ID = 5; // TODO modify the value

    @Test
    public void insert() throws Exception {
        Message entity = new Message();
        entity.setAddTime(new Date());
        entity.setFromId(1);
        entity.setToId(2);
        entity.setContent("Hello");
        entity.setStatus(0);
        dao.insert(entity);
        System.out.println(entity.getId());
    }

    @Test
    public void getById() throws Exception {
        Message entity = dao.getById(ID);
        System.out.println(entity.toString());
    }

    @Test
    public void getByField() throws Exception {
        List<Message> entityList = dao.getByField("", ""); // TODO fill fieldName and fieldValue
        for (Message entity : entityList) {
            System.out.println(entity.toString());
        }
    }

    @Test
    public void updateById() throws Exception {
        Message entity = new Message();
        // TODO fill new entity
        dao.updateById(ID, entity);
    }

    @Test
    public void deleteById() throws Exception {
        dao.deleteById(ID);
    }

}