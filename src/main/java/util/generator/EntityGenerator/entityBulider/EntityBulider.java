package util.generator.EntityGenerator.entityBulider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.text.StrSubstitutor;
import util.generator.EntityGenerator.AppConfig;
import util.generator.EntityGenerator.model.Column;
import util.generator.EntityGenerator.model.Table;
import util.generator.EntityGenerator.util.ColumnUtil;
import util.generator.EntityGenerator.util.PrimaryKeyUtil;
import util.generator.EntityGenerator.util.StringUtil;
import util.generator.EntityGenerator.util.TemplateUtil;

public class EntityBulider {
	public void buildBean(Table table) {
		StringBuilder beanTemplate = TemplateUtil.getTemplate("EntityTemplate");
		Map<String, String> valuesMap = new HashMap();

		valuesMap.put("entityPackageName", getEntityPackageName(table));
		valuesMap.put("generatedDate", new Date().toLocaleString());
		valuesMap.put("tableName", getTableName(table));
		valuesMap.put("entityClassName", table.getEntityClassName());
		boolean isNeedMultiplePKAttribute = false;
		if (table.getPrimaryKeyList().size() > 1) {
			isNeedMultiplePKAttribute = true;
		}
		valuesMap.put("keyConstructor", getKeyConstructor(isNeedMultiplePKAttribute, table));
		valuesMap.put("entityAllAttribute", getEntityAllAttribute(table));
		valuesMap.put("entityAllGetterAndSetter", getEntityAllGetterAndSetter(table));
		valuesMap.put("entityFieldToString", getEntityFieldToString(table));
		valuesMap.put("entityFieldToStringArgs", getEntityFieldToStringArgs(table));
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String outputString = sub.replace(beanTemplate);
		boolean isPKClass = false;
		File entityFile = getOutputFile(isPKClass, table.getEntityClassName());
		
		if (table.getPrimaryKeyList().size() > 1) {
			insertPrimaryKeyClass(table);
		}
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entityFile), "UTF-8"));
			writer.write(outputString);
		} catch (IOException e) {
			e.printStackTrace();
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getTableName(Table table) {
		return table.getTableName();
	}

	private void insertPrimaryKeyClass(Table table) {
		String primaryKeyClassName = PrimaryKeyUtil.getPrimaryKeyClassName(table);
		System.out.println("Start build " + table.getTableName() + "'s PK class: " + primaryKeyClassName);
		StringBuilder entityTemplate = TemplateUtil.getTemplate("MultiplePrimaryKeyTemplate");
		Map<String, String> valuesMap = new HashMap();

		valuesMap.put("entityPackageName", getEntityPackageName(table));
		valuesMap.put("entityPrimaryKeyClassName", primaryKeyClassName);
		boolean isNeedMultiplePKAttribute = false;
		valuesMap.put("keyConstructor", getKeyConstructor(isNeedMultiplePKAttribute, table));
		valuesMap.put("keyConstructorArgs", PrimaryKeyUtil.getKeyConstructorArgs(table));
		valuesMap.put("keyConstructorArgsAssign", PrimaryKeyUtil.getKeyConstructorArgsAssign(table));
		valuesMap.put("entityAllAttribute", PrimaryKeyUtil.getEntityAllAttribute(table));
		valuesMap.put("entityAllGetterAndSetter", PrimaryKeyUtil.getEntityAllGetterAndSetter(table));
		StrSubstitutor sub = new StrSubstitutor(valuesMap);

		String outputString = sub.replace(entityTemplate);
		boolean isPKClass = true;
		File entityFile = getOutputFile(isPKClass, table.getEntityClassName());

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entityFile), "UTF-8"));
			writer.write(outputString);
		} catch (IOException e) {
			e.printStackTrace();
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getKeyConstructor(boolean isNeedMultiplePKAttribute, Table table) {
		String keyConstructor = null;
		if (table.getPrimaryKeyList().size() == 0) {
			return System.getProperty("line.separator");
		}
		StringBuilder entityTemplate = TemplateUtil.getTemplate("KeyConstructorTemplate");
		Map<String, String> valuesMap = new HashMap();
		valuesMap.put("entityClassName", table.getEntityClassName());
		if (isNeedMultiplePKAttribute) {
			String primaryKeyClassName = PrimaryKeyUtil.getPrimaryKeyClassName(table);
			valuesMap.put("keyConstructorArgs", PrimaryKeyUtil.getKeyConstructorArgs(table));
			valuesMap.put("keyConstructorArgsAssign", PrimaryKeyUtil.getKeyConstructorArgsAssign(table));
		} else {
			String primaryKeyClassName = PrimaryKeyUtil.getPrimaryKeyClassName(table);
			valuesMap.put("entityClassName", primaryKeyClassName);
			valuesMap.put("keyConstructorArgs", getKeyConstructorArgs(table));
			valuesMap.put("keyConstructorArgsAssign", getKeyConstructorArgsAssign(table));
		}
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		keyConstructor = sub.replace(entityTemplate);
		return keyConstructor;
	}

	private String getKeyConstructorArgs(Table table) {
		StringBuilder sb = new StringBuilder();
		for (Column column : table.getColumnList()) {
			if (column.isPk()) {
				sb.append(column.getAttributeType() + " " + column.getAttributeName() + ", ");
			}
		}
		return sb.replace(sb.length() - 2, sb.length(), "").toString();
	}

	private String getKeyConstructorArgsAssign(Table table) {
		StringBuilder sb = new StringBuilder();
		for (Column column : table.getColumnList()) {
			if (column.isPk()) {
				sb.append("\t\tthis." + column.getAttributeName() + " = " + column.getAttributeName() + ";"
						+ System.getProperty("line.separator"));
			}
		}
		return sb.replace(sb.length() - 2, sb.length(), "").toString();
	}

	private String getEntityPackageName(Table table) {
		String tableName = table.getTableName();
		try {
			String[] parts = tableName.split("_");
			return AppConfig.rootPackageName + "." + parts[0].toLowerCase() + "."
					+ parts[1].substring(0, 3).toLowerCase() + ".model";
		} catch (Exception exception) {
		}
		return tableName;
	}

	private String getEntityAllAttribute(Table table) {
		StringBuilder sb = new StringBuilder();
		if (table.getPrimaryKeyList().size() > 1) {
			sb.append(ColumnUtil.getColumnAttributeForPK(table));
		}
		List<Column> columnList = table.getColumnList();
		for (Column column : columnList) {
			sb.append(ColumnUtil.getColumnAttribute(column));
		}
		return sb.toString();
	}

	private String getEntityAllGetterAndSetter(Table table) {
		StringBuilder sb = new StringBuilder();
		List<Column> columnList = table.getColumnList();
		for (Column column : columnList) {
			sb.append(ColumnUtil.getColumnGetterAndSetter(column));
		}
		return sb.toString();
	}

	private String getEntityFieldToString(Table table) {
		StringBuilder sb = new StringBuilder();
		List<Column> columnList = table.getColumnList();
		for (Column column : columnList) {
			sb.append(column.getAttributeName() + "=%s, ");
		}
		return sb.replace(sb.length() - 2, sb.length(), "").toString();
	}

	private String getEntityFieldToStringArgs(Table table) {
		StringBuilder sb = new StringBuilder();
		List<Column> columnList = table.getColumnList();
		for (Column column : columnList) {
			sb.append(column.getAttributeName() + ", ");
		}
		return sb.replace(sb.length() - 2, sb.length(), "").toString();
	}

	private File getOutputFile(boolean isPKClass, String className) {
		String path = null;
		if (isPKClass) {
			className = className.replace("Entity", "PK") + ".java";
		} else {
			className = className + ".java";
		}
		//File entityFile = new File(StringUtil.getOutputPath() + "/" + className);
		File entityFile = new File("D:/entityGenerator" + "/" + className);
		if (entityFile.exists()) {
			entityFile.delete();
			try {
				entityFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			new File(StringUtil.getOutputPath()).mkdirs();
			try {
				entityFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entityFile;
	}
}
