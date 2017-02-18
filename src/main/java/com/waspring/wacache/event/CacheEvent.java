package com.waspring.wacache.event;

import com.waspring.wacache.ICache;

/**
 * 事件来源
 * 
 * @author felly
 * 
 */
@SuppressWarnings("serial")
public class CacheEvent implements java.io.Serializable {
	private ICache source;// 此监听对象可以是自定义对象

	private Object key;
	private Object value;
	private int expire;

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public Object getKey() {
		return key;
	}

	public CacheEvent(ICache source, Object key, Object value) {
		this.source = source;
		this.key = key;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	private String name;// /事件名称

	public void setName(String name) {
		this.name = name;
	}

	public Object getSource() {
		return source;
	}

	public String getName() {
		return name;
	}

}
