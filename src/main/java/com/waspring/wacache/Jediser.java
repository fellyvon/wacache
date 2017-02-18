package com.waspring.wacache;

import redis.clients.jedis.Jedis;


 

/**
 * redis工具使用 抽象
 * 
 * @author felly
 * 
 */
public interface Jediser extends ICache {

    String bornKey(String key);
 
    long incr(String key);

    long decr(String key);
 

    long setex(String key, String value, int time);

    void returnResource(Jedis redis);

    void destory();

    public void returnBrokenResource(Jedis shardedJedis, Exception e);

    public long setCatceNx(Object key, Object value) throws Exception;

    // ///设置有过期限制的缓存
    public Object setCatceSet(Object key, Object value)
	    throws Exception;

}
