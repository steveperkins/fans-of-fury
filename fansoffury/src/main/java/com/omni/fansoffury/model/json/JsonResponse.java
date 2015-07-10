package com.omni.fansoffury.model.json;

import java.io.Serializable;
import java.util.List;

public class JsonResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String status;
	String message;
	List<String> errors;
	Object object;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public Boolean isError() {
		return null != errors && !errors.isEmpty();
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
