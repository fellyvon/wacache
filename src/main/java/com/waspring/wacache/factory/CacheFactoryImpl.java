package com.waspring.wacache.factory;

import com.waspring.wacache.ICache;
import com.waspring.wacache.ICacheFactory;
import com.waspring.wacache.exp.CacheException;

/**
 * 缓存生成工厂
 * 
 * @author felly
 * 
 */
public class CacheFactoryImpl implements ICacheFactory {

	@Override
	public ICache getCache(String categray) throws CacheException {
		String url = "com.waspring.wacache.factory.impl." + categray
				+ "Impl";

		ICache cache = null;
		try {
			cache = (ICache) Class.forName(url).newInstance();
		} catch (Exception e) {
			throw new CacheException("100:This operation is not supported!"
					+e.getMessage());
		}

		if (cache == null) {
			throw new CacheException("101：No cache instance！");
		}
		return cache;
	}

}
