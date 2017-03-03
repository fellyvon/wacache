# wacache
一款java缓存开发标准:
    >1.抽象并统一了目前流行缓存访问接口,概括目前流行的缓存框架,并且很容易继续扩展.
	>2.同时为缓存的写入,删除,读取提供监听事件支持.
github地址：https://github.com/fellyvon/wacache
#如何使用?
1.建立新工程并引入wacache-1.0.0.jar
   可以使用maven方式引入:
 ``` xml
     	<dependency>
			<groupId>com.waspring</groupId>
			<artifactId>wacache</artifactId>
			<version>1.0.0</version>
		</dependency>
```
2.建立java类并声明缓存工厂对象
 ``` java
       ICacheFactory cf = new CacheFactoryImpl();
        ///这里用于决定使用的缓存类型，目前有：OSCache\JCS\EHCache\Redis
  		ICache cache = cf.getCache(ICacheFactory.EHCache);
		///开始使用
		cache.set(key, value);//写入
		System.out.println("EHCache 1=" + cache.get(key));///读取
		
		cache.set(key, value, expire);///带超期的写入
		
		cache.del(key); ///清除缓存对象
		
		
 ```
 
#目前支持的缓存
目前有：OSCache\JCS\EHCache\Redis
本开发标准不对原来的框架做任何修改，只是通过门面将各种缓存框架的使用抽象统一，
所以各缓存框架的配置还是沿用原框架的配置（Redis除外）。

redis采用了单独的配置文件，在根目录放置redis.properties

可配置参数为：
        ///连接地址
		 redis.config.ip=
		 ///连接端口
		 redis.config.port=
		// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		 redis.config.blockWhenExhausted =true

		// 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
         redis.config.evictionPolicyClassName =

		// 是否启用pool的jmx管理功能, 默认true
		 redis.config.jmxEnabled =

		// 是否启用后进先出, 默认true
		 redis.config.lifo =

		// 最大空闲连接数, 默认8个
	     redis.config.maxIdle =

		// 最大连接数, 默认16个
		 redis.config.maxTotal =

		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
		// 默认-1
		 redis.config.maxWaitMillis =

		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		 redis.config.minEvictableIdleTimeMillis =

		// 最小空闲连接数, 默认0
		 redis.config.minIdle =

		// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		 redis.config.numTestsPerEvictionRun =
		// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数
		// 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)
		 redis.config.softMinEvictableIdleTimeMillis =

		// 在获取连接的时候检查有效性, 默认false
             redis.config.testOnBorrow =

		// 在空闲时检查有效性, 默认false
		 redis.config.testWhileIdle =
#事件监听
  缓存在写入,删除,读取时，可以添加监听器实现来处理统计，日志记录等。
  1、新建立类并扩展 com.waspring.wacache.event.ICacheListener
   实现全部监听方法即可。
    其中CacheEvent对象内保留了cache对象，key和value 等信息，可以取出用于记录。
  2、添加监听
   ``` java
  ICache cache = cf.getCache(ICacheFactory.EHCache);
		///开始使用
		cache.addListener(new  ICacheListener(){
		  /**
	 * 放入数据前的时候发生
	 * 
	 * @param evnt
	 */
	void setBeforeHandle(CacheEvent evnt){}

	/**
	 * 放入数据后发生
	 * 
	 * @param evnt
	 */
	void setAfterHandle(CacheEvent evnt){}

	/**
	 * 删除数据前的时候发生
	 * 
	 * @param evnt
	 */
	void delBeforeHandle(CacheEvent evnt){}

	/**
	 * 删除数据后发生
	 * 
	 * @param evnt
	 */
	void delAfterHandle(CacheEvent evnt){}

	/**
	 * 获取数据前的时候发生
	 * 
	 * @param evnt
	 */
	void getBeforeHandle(CacheEvent evnt){}

	/**
	 * 获取数据后发生
	 * 
	 * @param evnt
	 */
	void getAfterHandle(CacheEvent evnt){}
		});
 
 ```
 
 
 