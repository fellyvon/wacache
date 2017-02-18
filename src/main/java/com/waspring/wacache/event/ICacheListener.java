package com.waspring.wacache.event;

/**
 * 缓存事件定义
 * 
 * @author felly
 * 
 */
public interface ICacheListener {

	/**
	 * 放入数据前的时候发生
	 * 
	 * @param evnt
	 */
	void setBeforeHandle(CacheEvent evnt);

	/**
	 * 放入数据后发生
	 * 
	 * @param evnt
	 */
	void setAfterHandle(CacheEvent evnt);

	/**
	 * 删除数据前的时候发生
	 * 
	 * @param evnt
	 */
	void delBeforeHandle(CacheEvent evnt);

	/**
	 * 删除数据后发生
	 * 
	 * @param evnt
	 */
	void delAfterHandle(CacheEvent evnt);

	/**
	 * 获取数据前的时候发生
	 * 
	 * @param evnt
	 */
	void getBeforeHandle(CacheEvent evnt);

	/**
	 * 获取数据后发生
	 * 
	 * @param evnt
	 */
	void getAfterHandle(CacheEvent evnt);
}
