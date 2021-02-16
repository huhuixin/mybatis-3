package com.hhx.mybatis.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.impl.PerpetualCache;

import java.util.concurrent.locks.ReadWriteLock;

@Slf4j
public class Cache001 implements Cache {

    /**
     * 委派模式
     */
    private Cache delegate;

    public Cache001(String id) {
        delegate = new LruCache(new PerpetualCache(id));
    }

    public String getId() {
        log.info("Cache001 getId");
        return delegate.getId();
    }

    public void putObject(Object o, Object o1) {
        log.info("Cache001 putObject");
        delegate.putObject(o, o1);
    }

    public Object getObject(Object o) {
        log.info("Cache001 getObject");
        return delegate.getObject(o);
    }

    public Object removeObject(Object o) {
        log.info("Cache001 removeObject");
        return delegate.removeObject(o);
    }

    public void clear() {
        log.info("Cache001 clear");
        delegate.clear();
    }

    public int getSize() {
        log.info("Cache001 getSize");
        return delegate.getSize();
    }

    public ReadWriteLock getReadWriteLock() {
        log.info("Cache001 getReadWriteLock");
        return delegate.getReadWriteLock();
    }
}
