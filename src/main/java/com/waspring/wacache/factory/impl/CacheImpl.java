package com.waspring.wacache.factory.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.waspring.wacache.ICache;
import com.waspring.wacache.event.CacheEvent;
import com.waspring.wacache.event.ICacheListener;
import com.waspring.wacache.exp.CacheException;

/**
 * 缓存统一接口实现抽象
 * 
 * @author felly
 * 
 */
public abstract class CacheImpl implements ICache {

	protected List<ICacheListener> cl = new ArrayList<ICacheListener>();

	public abstract Object getInner(Object key) throws CacheException;

	public abstract Object setInner(Object key, Object value)
			throws CacheException;

	public abstract Object setInner(Object key, Object value, int expire)
			throws CacheException;

	public abstract void delInner(Object key) throws CacheException;

	@Override
	public Object get(Object key) {

		Object value = null;
		try {

			handle(EVENT_TYPE_GET, EVENT_OCC_BEFORE, new CacheEvent(this, key,
					value));
			return getInner(key);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

			handle(EVENT_TYPE_GET, EVENT_OCC_AFTER, new CacheEvent(this, key,
					value));
		}

	}

	@Override
	public Object set(Object key, Object value) {

		try {

			handle(EVENT_TYPE_SET, EVENT_OCC_BEFORE, new CacheEvent(this, key,
					value));
			return setInner(key, value);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

			handle(EVENT_TYPE_SET, EVENT_OCC_AFTER, new CacheEvent(this, key,
					value));
		}
	}

	@Override
	public Object set(Object key, Object value, int expire) {
		CacheEvent evt = new CacheEvent(this, key, value);
		evt.setExpire(expire);
		try {

			handle(EVENT_TYPE_SET, EVENT_OCC_BEFORE, evt);
			return setInner(key, value, expire);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

			handle(EVENT_TYPE_SET, EVENT_OCC_AFTER, evt);
		}
	}

	@Override
	public void del(Object key) {
		CacheEvent evt = new CacheEvent(this, key, null);
		evt.setExpire(0);
		try {

			handle(EVENT_TYPE_DEL, EVENT_OCC_BEFORE, evt);
			delInner(key);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

			handle(EVENT_TYPE_DEL, EVENT_OCC_AFTER, evt);
		}
	}

	@Override
	public void addListener(ICacheListener listner) {
		if (!cl.contains(listner)) {
			cl.add(listner);
		}
	}

	/**
	 * 判断是否有事件可执行
	 * 
	 * @return
	 */
	protected boolean haveEvent() {
		return !cl.isEmpty();
	}

	/**
	 * 事件的执行方法
	 */
	protected void handle(int eventType, int eventOcc, CacheEvent evnt) {
		if (!haveEvent()) {
			return;
		}
		Iterator<ICacheListener> ics = cl.iterator();
		while (ics.hasNext()) {
			ICacheListener next = ics.next();
			if (eventType == EVENT_TYPE_SET && eventOcc == EVENT_OCC_BEFORE) {
				next.setBeforeHandle(evnt);
			} else if (eventType == EVENT_TYPE_SET
					&& eventOcc == EVENT_OCC_AFTER) {
				next.setAfterHandle(evnt);
			} else if (eventType == EVENT_TYPE_DEL
					&& eventOcc == EVENT_OCC_BEFORE) {
				next.delBeforeHandle(evnt);
			} else if (eventType == EVENT_TYPE_DEL
					&& eventOcc == EVENT_OCC_AFTER) {
				next.getAfterHandle(evnt);
			} else if (eventType == EVENT_TYPE_GET
					&& eventOcc == EVENT_OCC_BEFORE) {
				next.delBeforeHandle(evnt);
			} else if (eventType == EVENT_TYPE_GET
					&& eventOcc == EVENT_OCC_AFTER) {
				next.getAfterHandle(evnt);
			}
		}
	}

	public static final int EVENT_TYPE_SET = 1;// 设置对象
	public static final int EVENT_TYPE_DEL = 2;// /删除

	public static final int EVENT_TYPE_GET = 3;// 获取

	public static final int EVENT_OCC_BEFORE = 1;// 前
	public static final int EVENT_OCC_AFTER = 2;// 后

}
