package com.medialink.rxjava2.model;

import com.google.gson.annotations.SerializedName;

public class CrudRespon extends BaseRespon{

	@SerializedName("fieldCount")
	private int fieldCount;

	@SerializedName("serverStatus")
	private int serverStatus;

	@SerializedName("protocol41")
	private boolean protocol41;

	@SerializedName("changedRows")
	private int changedRows;

	@SerializedName("affectedRows")
	private int affectedRows;

	@SerializedName("warningCount")
	private int warningCount;

	@SerializedName("message")
	private String message;

	@SerializedName("insertId")
	private int insertId;

	public void setFieldCount(int fieldCount){
		this.fieldCount = fieldCount;
	}

	public int getFieldCount(){
		return fieldCount;
	}

	public void setServerStatus(int serverStatus){
		this.serverStatus = serverStatus;
	}

	public int getServerStatus(){
		return serverStatus;
	}

	public void setProtocol41(boolean protocol41){
		this.protocol41 = protocol41;
	}

	public boolean isProtocol41(){
		return protocol41;
	}

	public void setChangedRows(int changedRows){
		this.changedRows = changedRows;
	}

	public int getChangedRows(){
		return changedRows;
	}

	public void setAffectedRows(int affectedRows){
		this.affectedRows = affectedRows;
	}

	public int getAffectedRows(){
		return affectedRows;
	}

	public void setWarningCount(int warningCount){
		this.warningCount = warningCount;
	}

	public int getWarningCount(){
		return warningCount;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setInsertId(int insertId){
		this.insertId = insertId;
	}

	public int getInsertId(){
		return insertId;
	}
}