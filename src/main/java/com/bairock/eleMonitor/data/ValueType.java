package com.bairock.eleMonitor.data;

/**
 * 值类型
 * @author 44489
 *
 */
public enum ValueType {

	VALUE(1, "数值量"),
	ALARM(2, "报警"),
	SWITCH(3, "开关量");
	
	private int code;
	private String info;
	
	private ValueType(int code, String info) {
		this.code = code;
		this.info = info;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
