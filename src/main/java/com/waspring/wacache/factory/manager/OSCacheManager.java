package com.waspring.wacache.factory.manager;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class OSCacheManager implements ICacheManager<GeneralCacheAdministrator> {
	private GeneralCacheAdministrator baseCache;
	private static OSCacheManager instance;
	private static Object lock = new Object();

	private OSCacheManager() {
		// 这个根据配置文件来，初始BaseCache
		baseCache = new GeneralCacheAdministrator();
	}

	/**
	 * <pre>
	 * getInstance(单例化一个   CacheManager 单例模式：双重判定锁)   
	 * @return CacheManager 对象
	 * </pre>
	 */
	public static OSCacheManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new OSCacheManager();
				}
			}
		}
		return instance;
	}

	public GeneralCacheAdministrator getBaseCache() {
		return baseCache;
	}

}
