package util.generator.EntityGenerator.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.text.StrSubstitutor;
import util.generator.EntityGenerator.model.Column;
import util.generator.EntityGenerator.model.Table;

public class ColumnUtil {
	public static String getColumnAttribute(Column column) {
		StringBuilder sb = TemplateUtil.getTemplate("ColumnAttributeTemplate");
		Map<String, String> valuesMap = new HashMap();
		if ((column.isPk()) && ("yes".equals(column.getIsAutoincrement().trim().toLowerCase()))) {
			sb.insert(0, "\t@Id" + System.getProperty("line.separator")
					+ "\t@GeneratedValue(strategy = GenerationType.AUTO)");
		} else if (column.isPk()) {
			sb.insert(0, "\t@Id" + System.getProperty("line.separator"));
		}
		valuesMap.put("columnName", column.getColumnName());
		valuesMap.put("attributeName", column.getAttributeName());
		valuesMap.put("attributeType", column.getAttributeType());

		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(sb.toString());
	}

	public static String getColumnAttributeForPK(Table table) {
		StringBuilder sb = TemplateUtil.getTemplate("ColumnAttributeForPKTemplate");
		String primaryKeyClassName = PrimaryKeyUtil.getPrimaryKeyClassName(table);
		Map<String, String> valuesMap = new HashMap();
		valuesMap.put("attributeName", StringUtil.toHeadLowerCase(primaryKeyClassName));
		valuesMap.put("attributeType", primaryKeyClassName);
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(sb.toString());
	}

	public static String getColumnGetterAndSetter(Column column) {
		StringBuilder sb = TemplateUtil.getTemplate("GetterSetterTemplate");
		Map<String, String> valuesMap = new HashMap();
		valuesMap.put("attributeName", column.getAttributeName());
		valuesMap.put("attributeType", column.getAttributeType());
		valuesMap.put("attributeSetter", "set" + StringUtil.toCamelCase(column.getColumnName()));
		valuesMap.put("attributeGetter", "get" + StringUtil.toCamelCase(column.getColumnName()));
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(sb.toString());
	}
}
