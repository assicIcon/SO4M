package com.assicIcon.SO4M.language.deiver.base;

import com.assicIcon.SO4M.constant.SqlConstant;
import com.assicIcon.SO4M.util.CaseUtil;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;

/**
 * @Author Runhu Wu
 * @Create 2019/3/4
 * @Desc 描述
 */
public class InsertSelectiveLanguageDriver extends BaseLanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        String tableName = super.getTableName(parameterType);
        StringBuilder columns = new StringBuilder();
        StringBuilder fields = new StringBuilder();
        columns.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
        fields.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
        for (Field field : parameterType.getDeclaredFields()) {
            columns.append(SqlConstant.insertIfNotNullColumn().replaceAll(SqlConstant.FIELD, field.getName()).replaceAll(SqlConstant.COLUMN, CaseUtil.caseToLowerUnderscore(field.getName())));
            fields.append(SqlConstant.insertIfNotNullField().replaceAll(SqlConstant.FIELD, field.getName()));
        }
        columns.append("</trim>");
        fields.append("</trim>");
        script = SqlConstant.insert(columns.toString() + fields.toString())
                .replaceAll(SqlConstant.TABLE, tableName);
        return super.createSqlSource(configuration, script, parameterType);
    }
}
