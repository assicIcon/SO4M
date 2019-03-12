package com.assicIcon.SO4M.language.deiver.base;

import com.assicIcon.SO4M.constant.SqlConstant;
import com.assicIcon.SO4M.util.CaseUtil;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import javax.persistence.Id;
import java.lang.reflect.Field;

public class DeleteByPrimaryKeyLanguageDriver extends BaseLanguageDriver {
    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        String tableName = super.getTableName(parameterType);
        String idFieldName = "";
        for(Field field : parameterType.getDeclaredFields()) {
            if(field.isAnnotationPresent(Id.class)) {
                idFieldName = field.getName();
            }
        }
        script = SqlConstant.delete().replaceAll(SqlConstant.TABLE, tableName)
                .replaceAll(SqlConstant.ID_COLUMN, CaseUtil.caseToLowerUnderscore(idFieldName))
                .replaceAll(SqlConstant.ID_FIELD, idFieldName);
        return super.createSqlSource(configuration, script, parameterType);
    }
}
