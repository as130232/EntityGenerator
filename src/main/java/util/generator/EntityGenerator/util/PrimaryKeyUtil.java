package util.generator.EntityGenerator.util;

import java.util.List;
import util.generator.EntityGenerator.model.Column;
import util.generator.EntityGenerator.model.Table;

public class PrimaryKeyUtil {
	public static String getPrimaryKeyClassName(Table table) {
		String primaryKeyClassName = StringUtil.toCamelCase(table.getTableName()) + "PK";
		return primaryKeyClassName;
	}

	public static String getKeyConstructorArgs(Table table) {
		String primaryKeyClassName = getPrimaryKeyClassName(table);
		StringBuilder sb = new StringBuilder();
		sb.append(primaryKeyClassName + " " + StringUtil.toHeadLowerCase(primaryKeyClassName));
		return sb.toString();
	}

	public static String getKeyConstructorArgsAssign(Table table) {
		String primaryKeyClassName = getPrimaryKeyClassName(table);
		StringBuilder sb = new StringBuilder();
		sb.append("\t\tthis." + StringUtil.toHeadLowerCase(primaryKeyClassName) + " = "
				+ StringUtil.toHeadLowerCase(primaryKeyClassName) + ";" + System.getProperty("line.separator"));
		return sb.toString();
	}

	public static String getEntityAllAttribute(Table table) {
		StringBuilder sb = new StringBuilder();
		List<Column> columnList = table.getColumnList();
		for (Column column : columnList) {
			if (column.isPk()) {
				sb.append(ColumnUtil.getColumnAttribute(column));
			}
		}
		return sb.toString();
	}

	public static String getEntityAllGetterAndSetter(Table table) {
		StringBuilder sb = new StringBuilder();
		List<Column> columnList = table.getColumnList();
		for (Column column : columnList) {
			if (column.isPk()) {
				sb.append(ColumnUtil.getColumnGetterAndSetter(column));
			}
		}
		return sb.toString();
	}
}
