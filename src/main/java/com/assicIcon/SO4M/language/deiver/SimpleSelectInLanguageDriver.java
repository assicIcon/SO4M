package com.assicIcon.SO4M.language.deiver;

import com.assicIcon.SO4M.constant.PatternContant;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Before:
 * @Select("
 * <script>
 *     select * from t_user where id in (
 *     <foreach item='item' index='index' collector='list' separator=','>
 *			#{item}
 *     </foreach>
 *     )
 * </script>
 * ")
 * List<user> getUsers(List<Integer> ids);
 *
 * after:
 * @Select(select * from t_user where id in (#{ids}))
 * @Lang(SimpleSelectInLanguageDriver.class)
 * List<user> getUsers(List<Integer> ids);
 */
public class SimpleSelectInLanguageDriver extends XMLLanguageDriver implements LanguageDriver {

	private static final Pattern pattern = Pattern.compile(PatternContant.ENTITY_PATTERN);

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
		Matcher matcher = pattern.matcher(script);
		if (matcher.find()) {
			script = matcher.replaceAll("<foreach item=\"_item\" index=\"index\" collector=\"$1\" separator=\",\"> #{_item} </foreach>");
			script = "<script>" + script + "</script>";
		}
		return super.createSqlSource(configuration, script, parameterType);
	}
}
