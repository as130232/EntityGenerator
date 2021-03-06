package util.generator.EntityGenerator.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.generator.EntityGenerator.AppConfig;
import util.generator.EntityGenerator.model.Column;
import util.generator.EntityGenerator.model.PrimaryKey;
import util.generator.EntityGenerator.model.Table;

public class DataBaseUtil {
	public static List<Table> getAllTables(Connection connection) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		ResultSet result = databaseMetaData.getTables(AppConfig.catalog, AppConfig.schemaPattern,
				AppConfig.tableNamePattern, AppConfig.types);

		List<Table> tableList = new ArrayList();
		while (result.next()) {
			Table table = new Table();
			table.setTableCat(result.getString(1));
			table.setTableSchem(result.getString(2));
			table.setTableName(result.getString(3));
			table.setTableType(result.getString(4));
			table.setRemarks(result.getString(5));

			tableList.add(table);

			setTableColumns(connection, table);
			setAllTablePk(connection, table);
		}
		return tableList;
	}

	private static void setTableColumns(Connection connection, Table table) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		List<Column> columnList = new ArrayList();
		ResultSet result = databaseMetaData.getColumns(table.getTableCat(), table.getTableSchem(), table.getTableName(),
				null);
		while (result.next()) {
			Column column = new Column();
			column.setTableCat(result.getString(1));
			column.setTableSchem(result.getString(2));
			column.setTableName(result.getString(3));
			column.setColumnName(result.getString(4));
			column.setDataType(result.getInt(5));
			column.setTypeName(result.getString(6));
			column.setColumnSize(result.getInt(7));
			column.setDecimalDigits(result.getInt(9));
			column.setNumPrecRadix(result.getInt(10));
			column.setNullable(result.getInt(11));
			column.setRemarks(result.getString(12));
			column.setColumnDef(result.getString(13));
			column.setSqlDataType(result.getInt(14));
			column.setSqlDatetimeSub(result.getInt(15));
			column.setCharOctetLength(result.getInt(16));
			column.setOrdinalPosition(result.getInt(17));
			column.setIsNullable(result.getString(18));
			column.setScopeCatalog(result.getString(19));
			column.setScopeSchema(result.getString(20));
			column.setScopeTable(result.getString(21));

			column.setIsAutoincrement(result.getString("IS_AUTOINCREMENT"));
			columnList.add(column);
		}
		table.setColumnList(columnList);
	}

	private static void setAllTablePk(Connection connection, Table table) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		List<PrimaryKey> primaryKeyList = new ArrayList<PrimaryKey>();
		ResultSet result = databaseMetaData.getPrimaryKeys(table.getTableCat(), table.getTableSchem(),
				table.getTableName());
		while (result.next()) {
			PrimaryKey primaryKey = new PrimaryKey();
			primaryKey.setTableCat(result.getString(1));
			primaryKey.setTableSchem(result.getString(2));
			primaryKey.setTableName(result.getString(3));
			primaryKey.setColumnName(result.getString(4));
			primaryKey.setKeySeq(result.getShort(5));
			primaryKey.setPkName(result.getString(6));
			primaryKeyList.add(primaryKey);
			for (Column column : table.getColumnList()) {
				if (column.getColumnName().equals(primaryKey.getColumnName())) {
					column.setPk(true);
				}
			}
		}
		table.setPrimaryKeyList(primaryKeyList);
	}
}
