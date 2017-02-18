package com.waspring.wacache.factory.impl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

import com.waspring.wacache.CacheConfig;
import com.waspring.wacache.Jediser;
import com.waspring.wacache.exp.CacheException;
import com.waspring.wacache.factory.ser.SerializeUtil;

/**
 * redis缓存管理类
 * 
 * @author felly
 * 
 */
public class RedisImpl extends CacheImpl implements Jediser {

	public Object setCatceSet(Object key, Object value) throws Exception {
		byte[] flag = new byte[0];
		Jedis jedis = null;
		if (key == null) {
			throw new Exception("不能存入空！");
		}
		try {
			jedis = pool.getResource();
			flag = jedis.getSet(bornKey(((String) key)).getBytes(),
					SerializeUtil.serialize(value));

		}

		catch (Exception e) {
			returnBrokenResource(jedis, e);
		}

		finally {
			returnResource(jedis);
		}

		if (flag.length != 0) {
			return SerializeUtil.unserialize(flag);
		}
		return null;
	}

	public long setCatceNx(Object key, Object value) throws Exception {
		long flag = 0;
		Jedis jedis = null;
		if (key == null) {
			throw new Exception("不能存入空！");
		}
		try {
			jedis = pool.getResource();
			flag = jedis.setnx(bornKey(((String) key)).getBytes(),
					SerializeUtil.serialize(value));

		}

		catch (Exception e) {
			returnBrokenResource(jedis, e);
		}

		finally {
			returnResource(jedis);
		}

		return flag;
	}

	public String bornKey(String key) {

		return key;

	}

	public synchronized void destory() {
		if (pool != null) {
			pool.destroy();
		}

	}

	public synchronized void setCatceEx(Object key, Object value, int timeout)
			throws CacheException {
		Jedis jedis = null;
		if (key == null) {
			throw new CacheException("key不能存入空！");
		}
		try {
			jedis = pool.getResource();
			jedis.setex(bornKey(((String) key)).getBytes(), timeout,
					SerializeUtil.serialize(value));
		}

		catch (Exception e) {
			returnBrokenResource(jedis, e);
		}

		finally {
			returnResource(jedis);
		}
	}

	public synchronized void returnBrokenResource(Jedis shardedJedis,
			Exception e) {
		try {
			if (shardedJedis != null) {
				if (handleJedisException(e)) {
					pool.returnBrokenResource(shardedJedis);
				} else {
					pool.returnResource(shardedJedis);
				}

			}
		} catch (Exception er) {
			e.printStackTrace();

		}
	}

	/**
	 * Handle jedisException, write log and return whether the connection is
	 * broken.
	 */
	protected synchronized boolean handleJedisException(Exception jedisException) {
		if (jedisException instanceof JedisConnectionException) {
			jedisException.printStackTrace();
		} else if (jedisException instanceof JedisDataException) {
			if ((jedisException.getMessage() != null)
					&& (jedisException.getMessage().indexOf("READONLY") != -1)) {
				jedisException.printStackTrace();
			} else {
				// dataException, isBroken=false
				return false;
			}
		} else {
			jedisException.printStackTrace();
		}
		return true;
	}

	private JedisPool pool = null;

	public JedisPool getPool() {
		return pool;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	public RedisImpl() {

		if (pool == null) {
			String ip = CacheConfig.getConfigValue("redis.config.ip");
			int port = CacheConfig.getConfigIntValue("redis.config.port");
			pool = new JedisPool(initConfig(), ip, port);
		}

	}

	private JedisPoolConfig initConfig() {
		JedisPoolConfig config = new JedisPoolConfig();

		// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		boolean blockWhenExhausted = Boolean.parseBoolean(CacheConfig
				.getConfigValue("redis.config.blockWhenExhausted", "true"));
		config.setBlockWhenExhausted(blockWhenExhausted);

		// 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)

		String evictionPolicyClassName = CacheConfig.getConfigValue(
				"redis.config.evictionPolicyClassName",
				"org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		config.setEvictionPolicyClassName(evictionPolicyClassName);

		// 是否启用pool的jmx管理功能, 默认true
		boolean jmxEnabled = Boolean.parseBoolean(CacheConfig.getConfigValue(
				"redis.config.jmxEnabled", "true"));

		config.setJmxEnabled(jmxEnabled);

		// MBean ObjectName = new
		// ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" +
		// "pool" + i); 默 认为"pool", JMX不熟

		String jmxNamePrefix = CacheConfig.getConfigValue(
				"redis.config.jmxNamePrefix", "pool");

		config.setJmxNamePrefix(jmxNamePrefix);

		// 是否启用后进先出, 默认true
		boolean lifo = Boolean.parseBoolean(CacheConfig.getConfigValue(
				"redis.config.lifo", "true"));

		config.setLifo(lifo);

		// 最大空闲连接数, 默认8个
		int maxIdle = Integer.parseInt(CacheConfig.getConfigValue(
				"redis.config.maxIdle", "8"));
		config.setMaxIdle(maxIdle);

		// 最大连接数, 默认16个
		int maxTotal = Integer.parseInt(CacheConfig.getConfigValue(
				"redis.config.maxIdle", "16"));
		config.setMaxTotal(maxTotal);

		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
		// 默认-1
		int maxWaitMillis = Integer.parseInt(CacheConfig.getConfigValue(
				"redis.config.maxWaitMillis", "-1"));
		config.setMaxWaitMillis(maxWaitMillis);

		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		int minEvictableIdleTimeMillis = Integer.parseInt(CacheConfig
				.getConfigValue("redis.config.minEvictableIdleTimeMillis",
						"1800000"));

		config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

		// 最小空闲连接数, 默认0
		int minIdle = Integer.parseInt(CacheConfig.getConfigValue(
				"redis.config.minIdle", "0"));

		config.setMinIdle(minIdle);

		// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		int numTestsPerEvictionRun = Integer.parseInt(CacheConfig
				.getConfigValue("redis.config.numTestsPerEvictionRun", "3"));

		config.setNumTestsPerEvictionRun(numTestsPerEvictionRun);

		// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数
		// 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)
		int softMinEvictableIdleTimeMillis = Integer.parseInt(CacheConfig
				.getConfigValue("redis.config.softMinEvictableIdleTimeMillis",
						"1800000"));

		config.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);

		// 在获取连接的时候检查有效性, 默认false

		boolean testOnBorrow = Boolean.parseBoolean(CacheConfig.getConfigValue(
				"redis.config.testOnBorrow", "false"));

		config.setTestOnBorrow(testOnBorrow);

		// 在空闲时检查有效性, 默认false
		boolean testWhileIdle = Boolean.parseBoolean(CacheConfig
				.getConfigValue("redis.config.testWhileIdle", "false"));

		config.setTestWhileIdle(testWhileIdle);

		return config;
	}

	/**
	 * 组装key
	 */

	public synchronized Object getInner(Object key) throws CacheException {
		if (key == null)
			return null;
		byte unser[] = null;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			unser = jedis.get(bornKey(((String) key)).getBytes());
		} catch (Exception e) {

			returnBrokenResource(jedis, e);
		} finally {
			returnResource(jedis);
		}
		if (unser == null)
			return null;

		try {
			return SerializeUtil.unserialize(unser);
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	public synchronized void delInner(Object key) throws CacheException {
		if (key == null) {
			throw new CacheException("Key不能存入空！");
		}
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.del(bornKey(((String) key)).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			returnBrokenResource(jedis, e);
		} finally {
			returnResource(jedis);
		}
	}

	public synchronized Object setInner(Object key, Object value)
			throws CacheException {
		if (key == null) {
			throw new CacheException("key不能存入空！");
		}
		Jedis jedis = null;
		try {

			jedis = pool.getResource();
			jedis.set(bornKey(((String) key)).getBytes(),
					SerializeUtil.serialize(value));
		} catch (Exception e) {

			returnBrokenResource(jedis, e);
		} finally {
			returnResource(jedis);
			return value;
		}
	}

	public synchronized long incr(String key) {
		Jedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			return shardedJedis.incr(bornKey(key));
		} catch (Exception ex) {

			returnBrokenResource(shardedJedis, ex);
		} finally {
			returnResource(shardedJedis);
		}
		return 0;
	}

	public synchronized long decr(String key) {
		Jedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			return shardedJedis.decr(bornKey(key));
		} catch (Exception ex) {

			returnBrokenResource(shardedJedis, ex);
		} finally {
			returnResource(shardedJedis);
		}
		return 0;
	}

	public long setex(String key, String value, int time) {

		Jedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			shardedJedis.setex(bornKey(key), time, value);
		} catch (Exception ex) {

			returnBrokenResource(shardedJedis, ex);
		} finally {
			returnResource(shardedJedis);
		}
		return 0;
	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	public synchronized void returnResource(Jedis redis) {
		if (redis != null) {
			try {

				pool.returnResource(redis);
			} catch (Exception e) {
				// e.printStackTrace();
				pool.returnBrokenResource(redis);
			}
		}
	}

	@Override
	public Object setInner(Object key, Object value, int expire)
			throws CacheException {
		setCatceEx(key, value, expire);
		return value;
	}

}
