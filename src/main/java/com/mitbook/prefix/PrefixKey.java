package com.mitbook.prefix;

/**
 * @author pengzhengfa
 */
public class PrefixKey extends BasePrefix {

    /**
     * 防止被外面实例化
     */
    private PrefixKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static PrefixKey with(int expireSeconds, String prefix) {
        return new PrefixKey(expireSeconds, prefix);
    }
}
