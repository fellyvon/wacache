package com.waspring.wacache.exp;

/**
 * 缓存异常
 * 
 * @author felly
 * 
 */
@SuppressWarnings("serial")
public class CacheException extends Exception {
	public CacheException(String errorInfo) {
		super(errorInfo);
	}
	
	public CacheException(Exception e) {
		super(e);
	}
}
