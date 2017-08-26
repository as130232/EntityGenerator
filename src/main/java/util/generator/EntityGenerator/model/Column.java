package util.generator.EntityGenerator.model;

import util.generator.EntityGenerator.util.StringUtil;

public class Column {
	private String tableCat;
	private String tableSchem;
	private String tableName;
	private String columnName;
	private int dataType;
	private String typeName;
	private int columnSize;
	private int decimalDigits;
	private int numPrecRadix;
	private int nullable;
	private String remarks;
	private String columnDef;
	private int sqlDataType;
	private int sqlDatetimeSub;
	private int charOctetLength;
	private int ordinalPosition;
	private String isNullable;
	private String scopeCatalog;
	private String scopeSchema;
	private String scopeTable;
	private short sourceDataType;
	private String isAutoincrement;
	private String isGeneratedcolumn;
	private boolean isPk = false;

	public boolean isPk() {
		return this.isPk;
	}

	public void setPk(boolean isPk) {
		this.isPk = isPk;
	}

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

	public int getDataType() {
		return this.dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getColumnSize() {
		return this.columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public int getDecimalDigits() {
		return this.decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public int getNumPrecRadix() {
		return this.numPrecRadix;
	}

	public void setNumPrecRadix(int numPrecRadix) {
		this.numPrecRadix = numPrecRadix;
	}

	public int getNullable() {
		return this.nullable;
	}

	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getColumnDef() {
		return this.columnDef;
	}

	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}

	public int getSqlDataType() {
		return this.sqlDataType;
	}

	public void setSqlDataType(int sqlDataType) {
		this.sqlDataType = sqlDataType;
	}

	public int getSqlDatetimeSub() {
		return this.sqlDatetimeSub;
	}

	public void setSqlDatetimeSub(int sqlDatetimeSub) {
		this.sqlDatetimeSub = sqlDatetimeSub;
	}

	public int getCharOctetLength() {
		return this.charOctetLength;
	}

	public void setCharOctetLength(int charOctetLength) {
		this.charOctetLength = charOctetLength;
	}

	public int getOrdinalPosition() {
		return this.ordinalPosition;
	}

	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getIsNullable() {
		return this.isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getScopeCatalog() {
		return this.scopeCatalog;
	}

	public void setScopeCatalog(String scopeCatalog) {
		this.scopeCatalog = scopeCatalog;
	}

	public String getScopeSchema() {
		return this.scopeSchema;
	}

	public void setScopeSchema(String scopeSchema) {
		this.scopeSchema = scopeSchema;
	}

	public String getScopeTable() {
		return this.scopeTable;
	}

	public void setScopeTable(String scopeTable) {
		this.scopeTable = scopeTable;
	}

	public short getSourceDataType() {
		return this.sourceDataType;
	}

	public void setSourceDataType(short sourceDataType) {
		this.sourceDataType = sourceDataType;
	}

	public String getIsAutoincrement() {
		return this.isAutoincrement;
	}

	public void setIsAutoincrement(String isAutoincrement) {
		this.isAutoincrement = isAutoincrement;
	}

	public String getIsGeneratedcolumn() {
		return this.isGeneratedcolumn;
	}

	public void setIsGeneratedcolumn(String isGeneratedcolumn) {
		this.isGeneratedcolumn = isGeneratedcolumn;
	}

	public String getAttributeName() {
		String attributeName = StringUtil.toCamelCase(this.columnName);
		return StringUtil.toHeadLowerCase(attributeName);
	}

	public String getAttributeType() {
		String attributeType = "String";
		if (this.dataType == -5) {
			attributeType = "Long";
		} else if ((this.dataType == 5) || (this.dataType == 4)) {
			attributeType = "Integer";
		} else if ((this.dataType == 2) || (this.dataType == 3) || (this.dataType == 8)) {
			attributeType = "BigDecimal";
		} else if ((this.dataType == 1) || (this.dataType == 12) || (this.dataType == -9) || (this.dataType == -15)) {
			attributeType = "String";
		} else if ((this.dataType == 91) || (this.dataType == 93)) {
			attributeType = "Date";
		}
		return attributeType;
	}
}
