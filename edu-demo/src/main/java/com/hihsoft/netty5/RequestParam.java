package com.hihsoft.netty5;

import java.io.Serializable;   

public class RequestParam implements Serializable {  
    /**
	 * 
	 */
	private static final long serialVersionUID = -2508213345224450149L; 
    private String type;  
    private Object data;
    
	public RequestParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RequestParam(String type, Object data) {
		super();
		this.type = type;
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "RequestParam [type=" + type + ", data=" + data + "]";
	} 
    
}  
