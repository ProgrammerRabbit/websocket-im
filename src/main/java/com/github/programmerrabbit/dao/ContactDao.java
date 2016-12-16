package com.github.programmerrabbit.dao;

import com.github.programmerrabbit.dao.entity.Contact;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Generated by Code-Generator.
 * Author: ProgrammerRabbit
 * http://github.com/ProgrammerRabbit
 */
public interface ContactDao {
    void insert(@Param("entity") Contact entity);

    @Select("SELECT * FROM Contact WHERE id = #{id}")
    Contact getById(@Param("id") int id);

    @Select("SELECT * FROM Contact WHERE ${fieldName} = #{fieldValue}")
    List<Contact> getByField(@Param("fieldName") String filedName, @Param("fieldValue") Object fieldValue);

    void updateById(@Param("id") int id, @Param("entity") Contact entity);

    @Delete("DELETE FROM Contact WHERE id = #{id}")
    void deleteById(@Param("id") int id);
}