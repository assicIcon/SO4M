package com.assicIcon.SO4M.language.deiver;

import com.google.common.base.CaseFormat;
import com.assicIcon.SO4M.annotation.Invisible;
import com.assicIcon.SO4M.constant.PatternContant;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Before
 * @Intert("insert into t_user (id, username) values (#{id}, #{username})")
 * void insert(User user)
 *
 * After
 * @Intert("insert into t_user (#{user})")
 * @Lang(SimpleInsertLanguageDriver.class)
 * void insert(User user)
 *
 */
public class SimpleInsertLanguageDriver extends XMLLanguageDriver implements LanguageDriver {

	private static final Pattern pattern = Pattern.compile(PatternContant.ENTITY_PATTERN);

	@Override
	public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
		Matcher matcher = pattern.matcher(script);
		if(matcher.find()) {
			StringBuilder columns = new StringBuilder();
			StringBuilder values = new StringBuilder();
			for(Field field : parameterType.getDeclaredFields()) {
				if(!field.isAnnotationPresent(Invisible.class)) {
					columns.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()) + ",");
					values.append("#{" + field.getName() + "},");
				}
			}
			columns.deleteCharAt(columns.lastIndexOf(","));
			values.deleteCharAt(values.lastIndexOf(","));
			script = matcher.replaceAll("(" + columns + ") values (" + values + ")");
		}
		return super.createSqlSource(configuration, script, parameterType);
	}
}
