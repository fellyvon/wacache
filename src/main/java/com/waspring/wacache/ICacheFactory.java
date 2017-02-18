package com.waspring.wacache;

import com.waspring.wacache.exp.CacheException;

/**
 * cache工厂
 * 
 * @author felly
 * 
 */
public interface ICacheFactory {

	/**
	 * OSCache是个一个广泛采用的高性能的J2EE缓存框架，OSCache能用于任何Java应用程序的普通的缓存解决方案。
	 * OSCache有以下特点： 缓存任何对象，你可以不受限制的缓存部分jsp页面或HTTP请求，任何java对象都可以缓存。
	 * 拥有全面的API--OSCache API给你全面的程序来控制所有的OSCache特性。
	 * 永久缓存--缓存能随意的写入硬盘，因此允许昂贵的创建(expensive-to-create)数据来保持缓存，甚至能让应用重启。
	 * 支持集群--集群缓存数据能被单个的进行参数配置，不需要修改代码。
	 * 缓存记录的过期--你可以有最大限度的控制缓存对象的过期，包括可插入式的刷新策略(如果默认性能不需要时)。 Java Caching system
	 * JSC(Java Caching
	 * system)是一个用分布式的缓存系统，是基于服务器的java应用程序。它是通过提供管理各种动态缓存数据来加速动态web应用。
	 * JCS和其他缓存系统一样
	 * ，也是一个用于高速读取，低速写入的应用程序。动态内容和报表系统能够获得更好的性能。如果一个网站，有重复的网站结构，使用间歇性更新方式的数据库
	 * (而不是连续不断的更新数据库)，被重复搜索出相同结果的，就能够通过执行缓存方式改进其性能和伸缩性。 EHCache EHCache
	 * 是一个纯java的在进程中的缓存，它具有以下特性：快速，简单，为Hibernate2.1充当可插入的缓存，最小的依赖性，全面的文档和测试。
	 * JCache
	 * JCache是个开源程序，正在努力成为JSR-107开源规范，JSR-107规范已经很多年没改变了。这个版本仍然是构建在最初的功能定义上。
	 * ShiftOne ShiftOne Java Object Cache是一个执行一系列严格的对象缓存策略的Java
	 * lib，就像一个轻量级的配置缓存工作状态的框架。 SwarmCache SwarmCache是一个简单且有效的分布式缓存，它使用IP
	 * multicast与同一个局域网的其他主机进行通讯，是特别为集群和数据驱动web应用程序而设计的。
	 * SwarmCache能够让典型的读操作大大超过写操作的这类应用提供更好的性能持。
	 * SwarmCache使用JavaGroups来管理从属关系和分布式缓存的通讯。 TreeCache / JBossCache
	 * JBossCache是一个复制的事务处理缓存
	 * ，它允许你缓存企业级应用数据来更好的改善性能。缓存数据被自动复制，让你轻松进行JBoss服务器之间的集群工作
	 * 。JBossCache能够通过JBoss应用服务或其他J2EE容器来运行一个MBean服务，当然，它也能独立运行。
	 * JBossCache包括两个模块：TreeCache和TreeCacheAOP。 TreeCache --是一个树形结构复制的事务处理缓存。
	 * TreeCacheAOP --是一个“面向对象”缓存，它使用AOP来动态管理POJO(Plain Old Java Objects)
	 * 注：AOP是OOP的延续，是Aspect Oriented Programming的缩写，意思是面向方面编程。 WhirlyCache
	 * Whirlycache是一个快速的
	 * 、可配置的、存在于内存中的对象的缓存。它能够通过缓存对象来加快网站或应用程序的速度，否则就必须通过查询数据库或其他代价较高的处理程序来建立。
	 * 
	 * @param categray
	 * @return
	 */
	public ICache getCache(String categray) throws CacheException;

	String OSCache = "OSCache";
	String JCS = "JCS";
	String EHCache = "EHCache";
	String ShiftOne = "ShiftOne";
	String SwarmCache = "SwarmCache";
	String JBossCache = "JBossCache";
	String WhirlyCache = "WhirlyCache";
	
	String Redis = "Redis";

}
