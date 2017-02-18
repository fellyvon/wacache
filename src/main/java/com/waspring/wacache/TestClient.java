package com.waspring.wacache;

import com.waspring.wacache.factory.CacheFactoryImpl;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String key="1";
		ICacheFactory
		 cf=new CacheFactoryImpl();

		 ICache cache=cf.getCache(ICacheFactory.EHCache);
		 cache.set(key, 2);
		 System.out.println("EHCache 1="+cache.get(key));
		 
		 cache.set(key, 3,1);
		 Thread.sleep(4000);
		 System.out.println("EHCache 2="+cache.get(key));
		 cache.set(key, 3,5);
		 cache.del(key);
		 
		 System.out.println("EHCache 3="+cache.get(key));
 
		 
		 cache=cf.getCache(ICacheFactory.OSCache);
		 cache.set(key, 2);
		 System.out.println("OSCache 1="+cache.get(key));
		 
		 cache.set(key, 3,1);
		 Thread.sleep(4000);
		 System.out.println("OSCache 2="+cache.get(key));
		 cache.set(key, 3,5);
		 cache.del(key);
		 
		 System.out.println("OSCache 3="+cache.get(key));
		 
		 cache=cf.getCache(ICacheFactory.JCS);
		 cache.set(key, 2);
		 System.out.println("JCS 1="+cache.get(key));
		 
		 cache.set(key, 3,5);
		 Thread.sleep(4000);
		 System.out.println("JCS 2="+cache.get(key));
		 cache.set(key, 3,5);
		 cache.del(key);
		 
		 System.out.println("JCS 3="+cache.get(key));
		 
		 ///.....
		 
		 cache=cf.getCache(ICacheFactory.Redis);
		 cache.set(key, 2);
		 System.out.println("Redis 1="+cache.get(key));
		 
		 cache.set(key, 3,1);
		 Thread.sleep(4000);
		 System.out.println("Redis 2="+cache.get(key));
		 cache.set(key, 3,5);
		 cache.del(key);
		 
		 System.out.println("Redis 3="+cache.get(key));
		 
		 
		 

	}

}
