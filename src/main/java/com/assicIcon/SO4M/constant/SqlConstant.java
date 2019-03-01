package com.assicIcon.SO4M.constant;

import java.util.List;

/**
 * @Author Runhu Wu
 * @Create 2019/3/1
 * @Desc 描述
 */
public class SqlConstant {

    public static final String TABLE = "_table";

    public static final String ID_COLUMN = "_column_id";

    public static final String ID_FIELD = "_field_id";

    public static final String COLUMN = "_column";

    public static final String FIELD = "_field";

    public static String selectByPrimaryKey() {
        String sql = "SELECT * FROM %s WHERE `%s` = #{%s}";
        return String.format(sql, TABLE, ID_COLUMN, ID_FIELD);
    }

    public static String insertIfColumnNotNull() {
        String sql = "<if test = \"%s != null\">`%s` = #{%s},</if>";
        return String.format(sql, FIELD, COLUMN, FIELD);
    }

    public static String insertIfColumnIsNull() {
        String sql = "<if test = \"%s == null\">`%s` = #{%s},</if>";
        return String.format(sql, FIELD, COLUMN, FIELD);
    }

    public static String updateByPrimaryKey(String script) {
        String sql = "UPDATE %s " + script + " WHERE `%s` = #{%s}";
        return String.format(sql, TABLE, ID_COLUMN, ID_FIELD);
    }

    public static String insert(String columns, String fields) {
        String sql = "INSERT INTO %s (" + columns + ") VALUES (" + fields + ")";
        return String.format(sql, TABLE);
    }

    public static String insert(List<String> columns, List<String> fields) {
        final StringBuffer column = new StringBuffer();
        final StringBuffer field = new StringBuffer();
        columns.forEach(e -> column.append("`" + e + "`,"));
        fields.forEach(e -> field.append("`" + e + "`,"));
        String sql = "INSERT INTO %s (" + column + ") VALUES (" + field + ")";
        return String.format(sql, TABLE);
    }

}
