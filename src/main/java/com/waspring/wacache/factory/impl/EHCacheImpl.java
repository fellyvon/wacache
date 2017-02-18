package com.waspring.wacache.factory.impl;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.opensymphony.oscache.web.filter.ExpiresRefreshPolicy;
import com.waspring.wacache.exp.CacheException;
import com.waspring.wacache.factory.manager.EHCacheManager;

/**
 * <p>
 * <span>Ehcache是一个很强大的轻量级框架，不依赖除了slf4j以外的任何包，这篇文章主要是了解一下ehcache的简单使用，
 * 对Ehcache做一个简单了解</span>
 * </p>
 * <p>
 * <span>首先要了解缓存清除策略，官方文档给出的有</span>
 * </p>
 * <p>
 * <span>LRU - least recently used（最近最少使用）</span>
 * </p>
 * <p>
 * <span>LFU - least frequently used（最不经常使用）</span>
 * </p>
 * <p>
 * <span>FIFO - first in first out, the oldest element by creation
 * time（清除最早缓存的数据，不关心是否经常使用）</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>使用配置文件的方式：</span>
 * </p>
 * <p>
 * <span>ehcache-test.xml</span>
 * </p>
 * <p>
 * <span>[html] view plain copy 在CODE上查看代码片派生到我的代码片</span>
 * </p>
 * <p>
 * <span>&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&lt;ehcache
 * xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp;
 * xsi:noNamespaceSchemaLocation=&quot;http://ehcache.org/ehcache.xsd&quot;&gt;
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &lt;diskStore
 * path=&quot;G:/development/workspace/test/WebContent
 * /cache/temporary&quot;/&gt;&lt;!-- 达到内存上限后缓存文件保存位置 --&gt; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &lt;defaultCache &nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; maxElementsInMemory=&quot;10000&quot;
 * &lt;!-- 最大内存占用，超出后缓存保存至文件 --&gt; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; memoryStoreEvictionPolicy=&quot;LRU&quot;
 * &lt;!-- 缓存废弃策略，LRU表示最少使用的优先清除，此值对应之前3种策略 --&gt; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; eternal=&quot;false&quot; &nbsp;
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; timeToIdleSeconds=&quot;1&quot; &lt;!--
 * 空闲时间，超出此时间未使用缓存自动清除 --&gt; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; timeToLiveSeconds=&quot;5&quot; &lt;!--
 * 清除时间，缓存保留的最长时间 --&gt; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; overflowToDisk=&quot;false&quot; &lt;!--
 * 是否往硬盘写缓存数据 --&gt; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; diskPersistent=&quot;false&quot; /&gt;
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &lt;!-- 测试 --&gt; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &lt;cache &nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; name=&quot;cache_test&quot; &lt;!-- 缓存名称
 * --&gt; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; memoryStoreEvictionPolicy=&quot;LRU&quot;
 * &nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; maxElementsInMemory=&quot;1&quot;
 * &nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; eternal=&quot;false&quot; &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; timeToIdleSeconds=&quot;7200&quot;
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; timeToLiveSeconds=&quot;7200&quot;
 * &nbsp;&nbsp;</span>
 * </p>
 * <p>
 * <span>&nbsp; &nbsp; &nbsp; &nbsp; overflowToDisk=&quot;true&quot; /&gt;
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>&lt;/ehcache&gt; &nbsp;</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>使用缓存首先要创建CacheManager，穿件方法有多种，此处使用create（URL）方法</span>
 * </p>
 * <p>
 * <span>[java] view plain copy 在CODE上查看代码片派生到我的代码片</span>
 * </p>
 * <p>
 * <span>CacheManager cacheManager = CacheManager.create(URL);//URL是指配置文件所在路径
 * 的URL
 * ，通常使用getClass().getResource(&quot;/config/ehcache/ehcache-test.xml&quot;)获取
 * &nbsp;</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>创建完成后就可以使用了，添加缓存</span>
 * </p>
 * <p>
 * <span>[java] view plain copy 在CODE上查看代码片派生到我的代码片</span>
 * </p>
 * <p>
 * <span>//key:根据此值获取缓存的value，不可重复，value值需要缓存的数据 &nbsp;</span>
 * </p>
 * <p>
 * <span>Element element = new Element(key, value); &nbsp;</span>
 * </p>
 * <p>
 * <span>//cacheName:指ehcache-test.xml配置文件中的缓存名称 name=&quot;xxx&quot;中的值
 * &nbsp;</span>
 * </p>
 * <p>
 * <span>Cache cache = cacheManager.getCache(cacheName); &nbsp;</span>
 * </p>
 * <p>
 * <span>cache.put(element); &nbsp;</span>
 * </p>
 * <p>
 * <span><br/>
 * </span>
 * </p>
 * <p>
 * <span>获取缓存</span>
 * </p>
 * <p>
 * <span>[java] view plain copy 在CODE上查看代码片派生到我的代码片</span>
 * </p>
 * <p>
 * <span>Cache cache = cacheManager.getCache(cacheName); &nbsp;</span>
 * </p>
 * <p>
 * <span>Element element = cache.get(key); &nbsp;</span>
 * </p>
 * <p>
 * <span>Object data = element.getObjectValue();//获取到的缓存数据 &nbsp;</span>
 * </p>
 * <p>
 * <br/>
 * </p>
 * 
 * @author felly
 * 
 */
public class EHCacheImpl extends CacheImpl {
	private Cache gca = null;

	public EHCacheImpl() {
		gca = EHCacheManager.getInstance().getBaseCache();

	}

	@Override
	public Object getInner(Object key) throws CacheException {
		try {
			Element ele = gca.get(key);
			if (ele == null) {
				return null;
			}
			return ele.getObjectValue();
		} catch (Exception e) {

			throw new CacheException(e);
		}

	}

	@Override
	public Object setInner(Object key, Object value) throws CacheException {
		try {
			// key:根据此值获取缓存的value，不可重复，value值需要缓存的数据
			Element element = new Element(key, value);

			gca.put(element);
			return value;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public Object setInner(Object key, Object value, int expire)
			throws CacheException {
		try {
			// key:根据此值获取缓存的value，不可重复，value值需要缓存的数据
			Element element = new Element(key, value);
			element.setTimeToIdle(expire);
			gca.put(element);
			return value;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void delInner(Object key) throws CacheException {

		try {
			gca.remove(key);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

}
