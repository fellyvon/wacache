package com.waspring.wacache.factory.manager;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

public class JCSManager  implements ICacheManager<JCS>{
	private JCS baseCache;
	private static JCSManager instance;
	private static Object lock = new Object();

	private JCSManager() {
		// 这个根据配置文件来，初始BaseCache
		try {
			baseCache = JCS.getInstance("default");
		} catch (CacheException e) {
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
	public static JCSManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new JCSManager();
				}
			}
		}
		return instance;
	}

	public JCS getBaseCache() {
		return baseCache;
	}

}
