package com.assicIcon.SO4M.language.deiver;

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
 * @Lang(SimpleUpdateLanguageDriver.class)
 * void insert(User user)
 *
 */
public class SimpleUpdateLanguageDriver extends XMLLanguageDriver implements LanguageDriver{

	private static final Pattern pattern = Pattern.compile(PatternContant.ENTITY_PATTERN);

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
		Matcher matcher = pattern.matcher(script);
		if (matcher.find()) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("<set>");
			for (Field field : parameterType.getDeclaredFields()) {
				if (!field.isAnnotationPresent(Invisible.class) && !field.isAnnotationPresent(Id.class)) {
					stringBuilder.append(SqlConstant.insertIfColumnNotNull()
							.replaceAll(SqlConstant.COLUMN, CaseUtil.caseToLowerUnderscore(field.getName()))
							.replaceAll(SqlConstant.FIELD, field.getName()));
				}
			}
			stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
			stringBuilder.append("</set>");
			script = "<script>" + matcher.replaceAll(stringBuilder.toString()) + "</script>";
		}
		return super.createSqlSource(configuration, script, parameterType);
	}
}
