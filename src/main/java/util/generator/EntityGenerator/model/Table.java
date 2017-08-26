package util.generator.EntityGenerator.model;

import java.util.List;
import util.generator.EntityGenerator.util.StringUtil;

public class Table {
	private String tableCat;
	private String tableSchem;
	private String tableName;
	private String tableType;
	private String remarks;
	private String typeCat;
	private String typeSchem;
	private String typeName;
	private String selfReferencingColName;
	private String refGeneration;
	private List<Column> columnList;
	private List<PrimaryKey> primaryKeyList;

	public List<PrimaryKey> getPrimaryKeyList() {
		return this.primaryKeyList;
	}

	public void setPrimaryKeyList(List<PrimaryKey> primaryKeyList) {
		this.primaryKeyList = primaryKeyList;
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

	public String getTableType() {
		return this.tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTypeCat() {
		return this.typeCat;
	}

	public void setTypeCat(String typeCat) {
		this.typeCat = typeCat;
	}

	public String getTypeSchem() {
		return this.typeSchem;
	}

	public void setTypeSchem(String typeSchem) {
		this.typeSchem = typeSchem;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSelfReferencingColName() {
		return this.selfReferencingColName;
	}

	public void setSelfReferencingColName(String selfReferencingColName) {
		this.selfReferencingColName = selfReferencingColName;
	}

	public String getRefGeneration() {
		return this.refGeneration;
	}

	public void setRefGeneration(String refGeneration) {
		this.refGeneration = refGeneration;
	}

	public List<Column> getColumnList() {
		return this.columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	public String getEntityClassName() {
		return StringUtil.toCamelCase(this.tableName) + "Entity";
	}
}
