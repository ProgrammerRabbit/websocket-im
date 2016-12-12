package com.github.programmerrabbit.dao;

import com.github.programmerrabbit.entity.Account;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Rabbit on 2016/12/12.
 */
public interface AccountDao {
    void insert(@Param("entity") Account entity);

    @Select("SELECT * FROM Account WHERE id = #{id}")
    Account getById(@Param("id") int id);

    @Select("SELECT * FROM Account WHERE #{fieldName} = #{fieldValue}")
    List<Account> getByField(@Param("fieldName") String filedName, @Param("fieldValue") Object fieldValue);

    void updateById(@Param("id") int id, @Param("entity") Account entity);

    @Delete("DELETE FROM Account WHERE id = #{id}")
    void deleteById(@Param("id") int id);
}
