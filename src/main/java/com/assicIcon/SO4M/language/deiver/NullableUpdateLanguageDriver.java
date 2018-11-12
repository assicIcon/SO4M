package com.assicIcon.SO4M.language.deiver;

import com.assicIcon.SO4M.annotation.Invisible;
import com.assicIcon.SO4M.constant.PatternContant;
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
public class NullableUpdateLanguageDriver extends XMLLanguageDriver implements LanguageDriver {

	private static final Pattern pattern = Pattern.compile(PatternContant.ENTITY_PATTERN);

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
		Matcher matcher = pattern.matcher(script);
		if (matcher.find()) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("<set>");
			for (Field field : parameterType.getDeclaredFields()) {
				if (!field.isAnnotationPresent(Invisible.class) && !field.isAnnotationPresent(Id.class)) {
					stringBuilder.append(("<if test=\"_field != null\"> _column = #{_field}, </if>" +
							"<if test=\"_field == null\"> _column = null,</if>")
							.replaceAll("_column", CaseUtil.caseToLowerUnderscore(field.getName()))
							.replaceAll("_field", field.getName()));
				}
			}
			stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
			stringBuilder.append("</set>");
			script = "<script>" + matcher.replaceAll(stringBuilder.toString()) + "</script>";
		}
		return super.createSqlSource(configuration, script, parameterType);
	}
}
