package com.assicIcon.SO4M.language.deiver.base;

import com.assicIcon.SO4M.annotation.Invisible;
import com.assicIcon.SO4M.constant.PatternContant;
import com.assicIcon.SO4M.constant.SqlConstant;
import com.assicIcon.SO4M.util.CaseUtil;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Before :
 * @Update("
 * <script>
 *     update t_user
 *     <set>
 *         <if test=\"id != null\">
 *				id = #{id}
 *         </if>
 *         <if test=\"username != null\">
 *             username = #{username}
 *         </if>
 *     </set>
 *     where id = #{id}
 * </script>")
 * void insert(User user)
 *
 * After :
 * @Update("update t_user (#{user}) where id = #{id}")
 * @Lang(NullableUpdateLanguageDriver.class)
 * void insert(User user)
 *
 * 注意：user对象中的空属性将会被设置到数据库中
 * tip: The empty attributes in user will be set up to the database
 */
public class UpdateByPrimaryKeyLanguageDriver extends BaseLanguageDriver {

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        String tableName = super.getTableName(parameterType);
        String idFieldName = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : parameterType.getDeclaredFields()) {
            if(field.isAnnotationPresent(Id.class)) {
                idFieldName = field.getName();
            } else {
                stringBuilder.append(String.format("`%s` = #{%s},", CaseUtil.caseToLowerUnderscore(field.getName()), field.getName()));
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        script = SqlConstant.updateByPrimaryKey(stringBuilder.toString())
                .replaceAll(SqlConstant.TABLE, tableName)
                .replace(SqlConstant.ID_COLUMN, CaseUtil.caseToLowerUnderscore(idFieldName))
                .replace(SqlConstant.ID_FIELD, idFieldName);
		return super.createSqlSource(configuration, script, parameterType);
	}
}
