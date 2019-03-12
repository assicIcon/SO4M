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

    public static String selectByCondition(String script) {
        String sql = "<script>SELECT * FROM %s WHERE 1 = 1 %s</script>";
        return String.format(sql, TABLE, script);
    }

    public static String whereIfColumnNotNull() {
        String sql = "<if test = \"%s != null\"> AND `%s` = #{%s}</if>";
        return String.format(sql, FIELD, COLUMN, FIELD);
    }

    public static String updateIfColumnNotNull() {
        String sql = "<if test = \"%s != null\">`%s` = #{%s},</if>";
        return String.format(sql, FIELD, COLUMN, FIELD);
    }

    public static String updateIfColumnIsNull() {
        String sql = "<if test = \"%s == null\">`%s` = #{%s},</if>";
        return String.format(sql, FIELD, COLUMN, FIELD);
    }

    public static String updateByPrimaryKey(String script) {
        String sql = "<script>UPDATE %s <set> " + script + " </set> WHERE `%s` = #{%s}</script>";
        return String.format(sql, TABLE, ID_COLUMN, ID_FIELD);
    }

    public static String insert(String script) {
        String sql = "<script>INSERT INTO %s " + script + "</script>";
        return String.format(sql, TABLE);
    }

    public static String insert(String columns, String fields) {
        String sql = "<script>INSERT INTO %s (" + columns + ") VALUES (" + fields + ")</script>";
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

    public static String insertIfNotNullField() {
        String sql = "<if test=\"%s != null\" >#{%s},</if>";
        return String.format(sql, FIELD, FIELD);
    }

    public static String insertIfNotNullColumn() {
        String sql = "<if test=\"%s != null\" >%s,</if>";
        return String.format(sql, FIELD, COLUMN);
    }

    public static String trim(String prefix, String suffix, String suffixOverrides, String Content) {
        String xml = "<trim prefix=\"%s\" suffix=\"%s\" suffixOverrides=\"%s\">%s</trim>";
        return String.format(xml, prefix, suffix, suffixOverrides, Content);
    }

    public static String delete()  {
        String sql = "delete %s where `%s` = #{%s}";
        return String.format(sql, TABLE, ID_COLUMN, ID_FIELD);
    }

}
