package com.waspring.wacache.factory.manager;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

public class EHCacheManager implements ICacheManager<Cache>{
	private Cache baseCache;
	private static EHCacheManager instance;
	private static Object lock = new Object();
	public static final String CACHE_NAME = "wacache_ehcache";

	private EHCacheManager() {
		// 这个根据配置文件来，初始BaseCache
		try {
			// 创建一个缓存管理器
			CacheManager singletonManager = CacheManager.create();
		 
			Cache cache = singletonManager.getCache(CACHE_NAME);
			baseCache = cache;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * getInstance(单例化一个   CacheManager 单例模式：双重判定锁)   
	 * @return CacheManager 对象
	 * </pre>
	 */
	public static EHCacheManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new EHCacheManager();
				}
			}
		}
		return instance;
	}

	public Cache getBaseCache() {
		return baseCache;
	}

}
