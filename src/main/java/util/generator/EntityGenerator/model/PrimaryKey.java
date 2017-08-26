package util.generator.EntityGenerator.model;

public class PrimaryKey {
	private String tableCat;
	private String tableSchem;
	private String tableName;
	private String columnName;
	private short keySeq;
	private String pkName;

	public String getTableCat() {
		return this.tableCat;
	}

	public void setTableCat(String tableCat) {
		this.tableCat = tableCat;
	}

	public String getTableSchem() {
		return this.tableSchem;
	}

	public void setTableSchem(String tableSchem) {
		this.tableSchem = tableSchem;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public short getKeySeq() {
		return this.keySeq;
	}

	public void setKeySeq(short keySeq) {
		this.keySeq = keySeq;
	}

	public String getPkName() {
		return this.pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
}
