package com.waspring.wacache;

import com.waspring.wacache.event.ICacheListener;

/**
 * 缓存抽象接口
 * 
 * @author felly
 * 
 */
public interface ICache {

	/**
	 * 获取缓存值，不存在值和不存在key统一返回null
	 * 
	 * @param key
	 * @return
	 */
	Object get(Object key);

	/**
	 * 将值放入缓存，无过期
	 * 
	 * @param key
	 * @param value
	 * @return 设置后的值
	 */
	Object set(Object key, Object value);

	/**
	 * 将值放入缓存，有过期设置
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 *            单位秒
	 * @return 设置后的值
	 */
	Object set(Object key, Object value, int expire);

	/**
	 * 显出缓存对象，返回删除前的缓存对象
	 * 
	 * @param key
	 * @return
	 */
	void del(Object key);

	/**
	 * 添加监听器
	 * 
	 * @param listner
	 */
	void addListener(ICacheListener listner);

}
