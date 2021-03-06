package com.github.programmerrabbit.dao;

import com.github.programmerrabbit.dao.entity.Message;
import com.github.programmerrabbit.dto.MessageQueryDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    List<Message> getMessagesByQueryDto(@Param("query") MessageQueryDto queryDto);

    int getMessagesCountByQueryDto(@Param("query") MessageQueryDto queryDto);

    @Update("UPDATE Message SET status = #{status} WHERE toId = #{toId} AND fromId = #{fromId}")
    void updateChatMessagesStatus(@Param("toId") int toId, @Param("fromId") int fromId, @Param("status") int status);
}