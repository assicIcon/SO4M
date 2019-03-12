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
import javax.persistence.Table;
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
 * void update(User user)
 *
 * After :
 * @Update("update t_user (#{user})")
 * @Lang(UpdateByPrimaryKeyLanguageDriver.class)
 * void insert(User user)
 *
 */
public class UpdateByPrimaryKeySelectiveLanguageDriver extends BaseLanguageDriver {

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        String table = super.getTableName(parameterType);
        StringBuilder stringBuilder = new StringBuilder();
        String idFieldName = null;
        for (Field field : parameterType.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Invisible.class) && !field.isAnnotationPresent(Id.class)) {
                stringBuilder.append(SqlConstant.updateIfColumnNotNull()
                        .replaceAll(SqlConstant.COLUMN, CaseUtil.caseToLowerUnderscore(field.getName()))
                        .replaceAll(SqlConstant.FIELD, field.getName()));
            }
            if (field.isAnnotationPresent(Id.class)) {
                idFieldName = field.getName();
            }
        }
        script = SqlConstant.updateByPrimaryKey(stringBuilder.toString())
                .replace(SqlConstant.TABLE, table)
                .replace(SqlConstant.ID_COLUMN, CaseUtil.caseToLowerUnderscore(idFieldName))
                .replace(SqlConstant.ID_FIELD, idFieldName);
		return super.createSqlSource(configuration, script, parameterType);
	}
}
