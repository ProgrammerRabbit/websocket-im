package com.github.programmerrabbit.dao;

import com.github.programmerrabbit.dao.entity.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Generated by Code-Generator.
 * Author: ProgrammerRabbit
 * http://github.com/ProgrammerRabbit
 */
public interface MessageDao {
    void insert(@Param("entity") Message entity);

    @Select("SELECT * FROM Message WHERE id = #{id}")
    Message getById(@Param("id") int id);

    @Select("SELECT * FROM Message WHERE ${fieldName} = #{fieldValue}")
    List<Message> getByField(@Param("fieldName") String filedName, @Param("fieldValue") Object fieldValue);

    void updateById(@Param("id") int id, @Param("entity") Message entity);

    @Delete("DELETE FROM Message WHERE id = #{id}")
    void deleteById(@Param("id") int id);
}