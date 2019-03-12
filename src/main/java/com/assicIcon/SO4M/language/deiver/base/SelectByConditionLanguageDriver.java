package com.assicIcon.SO4M.language.deiver.base;

import com.assicIcon.SO4M.constant.SqlConstant;
import com.assicIcon.SO4M.util.CaseUtil;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import javax.persistence.Id;
import java.util.Arrays;

public class SelectByConditionLanguageDriver extends BaseLanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        String tableName = super.getTableName(parameterType);
        StringBuilder sql = new StringBuilder();
        Arrays.asList(parameterType.getDeclaredFields()).forEach(e -> {
            if (!e.isAnnotationPresent(Id.class)) {
                sql.append(SqlConstant.whereIfColumnNotNull()
                        .replaceAll(SqlConstant.FIELD, e.getName())
                        .replaceAll(SqlConstant.COLUMN, CaseUtil.caseToLowerUnderscore(e.getName())) + "\n");
            }
        });
        script = SqlConstant.selectByCondition(sql.toString()).replaceAll(SqlConstant.TABLE, tableName);
        return super.createSqlSource(configuration, script, parameterType);
    }
}
