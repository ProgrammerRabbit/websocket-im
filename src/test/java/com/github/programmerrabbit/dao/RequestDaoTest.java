package com.github.programmerrabbit.dao;

import com.github.programmerrabbit.BaseTest;
import com.github.programmerrabbit.dao.entity.Request;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Generated by Code-Generator.
 * Author: ProgrammerRabbit
 * http://github.com/ProgrammerRabbit
 */
public class RequestDaoTest extends BaseTest {
    @Resource
    private RequestDao dao;

    private final static int ID = 1; // TODO modify the value

    @Test
    public void insert() throws Exception {
        Request entity = new Request();
        // TODO fill entity
        dao.insert(entity);
        System.out.println(entity.getId());
    }

    @Test
    public void getById() throws Exception {
        Request entity = dao.getById(ID);
        System.out.println(entity.toString());
    }

    @Test
    public void getByField() throws Exception {
        List<Request> entityList = dao.getByField("", ""); // TODO fill fieldName and fieldValue
        for (Request entity : entityList) {
            System.out.println(entity.toString());
        }
    }

    @Test
    public void updateById() throws Exception {
        Request entity = new Request();
        // TODO fill new entity
        dao.updateById(ID, entity);
    }

    @Test
    public void deleteById() throws Exception {
        dao.deleteById(ID);
    }

    @Test
    public void testGetRequest() throws Exception {
        System.out.println(dao.getRequestByRequestAndAcceptUserId(1, 2));
    }
}