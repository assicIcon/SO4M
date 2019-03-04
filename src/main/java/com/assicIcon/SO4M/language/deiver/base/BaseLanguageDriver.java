package com.assicIcon.SO4M.language.deiver.base;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import javax.persistence.Table;

/**
 * @Author Runhu Wu
 * @Create 2019/3/4
 * @Desc 描述
 */
public abstract class BaseLanguageDriver extends XMLLanguageDriver implements LanguageDriver {

    public final String getTableName(Class<?> parameterType) {
        Table table = parameterType.getAnnotation(Table.class);
        return table.name();
    }

}
