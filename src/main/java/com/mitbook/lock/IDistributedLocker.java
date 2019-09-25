package com.mitbook.lock;

import java.util.concurrent.TimeUnit;

/**
 * 锁分配接口
 *
 * @author pengzhengfa
 */
public interface IDistributedLocker {
    void lock(String lockKey);

    void unlock(String lockKey);

    void lock(String lockKey, int timeout);

    void lock(String lockKey, TimeUnit unit, int timeout);

    void deleteLock(String lockKey);

    boolean isExistLock(String lockKey);
}
