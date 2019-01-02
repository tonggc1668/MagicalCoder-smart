package com.magicalcoder.youyamvc.core.common.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * 错误代码
	 */
	private String errorCode = null;
	public BusinessException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	public BusinessException(String errorCode, String errorMessage, Throwable t) {
		super(errorMessage, t);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	/**
	 * 异常堆栈增加错误代码和绑定变量
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("错误代码[").append(this.errorCode).append("]\n");
		sb.append(super.toString());
		return sb.toString();
	}
}
