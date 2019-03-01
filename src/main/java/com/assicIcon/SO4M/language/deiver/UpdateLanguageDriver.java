package com.assicIcon.SO4M.language.deiver;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

/**
 * @Author Runhu Wu
 * @Create 2019/3/1
 * @Desc 描述
 */
public class UpdateLanguageDriver extends XMLLanguageDriver implements LanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

        return super.createSqlSource(configuration, script, parameterType);
    }
}
