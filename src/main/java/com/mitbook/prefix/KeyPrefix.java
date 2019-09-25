package com.mitbook.prefix;

/**
 * 缓冲key前缀
 *
 * @author pengzhengfa
 */
public interface KeyPrefix {

    /**
     * 有效期
     *
     * @return
     */
    public int expireSeconds();

    /**
     * 前缀
     *
     * @return
     */
    public String getPrefix();
}
