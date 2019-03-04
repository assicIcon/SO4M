package com.assicIcon.SO4M.basic;

import com.assicIcon.SO4M.language.deiver.base.*;
import org.apache.ibatis.annotations.*;

/**
 * @Author Runhu Wu
 * @Create 2019/3/4
 * @Desc 描述
 */
public interface BaseMapper<T> {

    @Select("")
    @Lang(SelectByPrimaryKeyLanguageDriver.class)
    T selectByPrimaryKey(T entity);

    @Insert("")
    @Lang(InsertSelectiveLanguageDriver.class)
    int insertSelective(T entity);

    @Insert("")
    @Lang(InsertLanguageDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(T entity);

    @Update("")
    @Lang(UpdateByPrimaryKeySelectiveLanguageDriver.class)
    int updateByPrimaryKeySelective(T entity);

    @Update("")
    @Lang(UpdateByPrimaryKeyLanguageDriver.class)
    int updateByPrimaryKey(T entity);


}
