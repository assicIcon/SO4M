package com.assicIcon.SO4M.language.deiver.base;

import com.assicIcon.SO4M.constant.SqlConstant;
import com.assicIcon.SO4M.util.CaseUtil;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;

/**
 * @Author Runhu Wu
 * @Create 2019/3/1
 * @Desc 描述
 */
public class SelectByPrimaryKeyLanguageDriver extends BaseLanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        if(parameterType.isAnnotationPresent(Table.class)) {
            script = SqlConstant.selectByPrimaryKey();
            String tableName = super.getTableName(parameterType);
            script = script.replace(SqlConstant.TABLE, tableName);
            for(Field field : parameterType.getDeclaredFields()) {
                if(field.isAnnotationPresent(Id.class)) {
                    script = script.replace(SqlConstant.ID_COLUMN, CaseUtil.caseToLowerUnderscore(field.getName()))
                            .replace(SqlConstant.ID_FIELD, field.getName());
                }
            }
        }
        return super.createSqlSource(configuration, script, parameterType);
    }
}
