package org.liuzhibin.research.mycat.service;

public class BizException extends RuntimeException {
	private static final long serialVersionUID = 8217497375119697236L;
	public BizException(String message) {
		super(message);
	}
	public BizException(String message, Throwable e) {
		super(message, e);
	}
}