package com.mitbook;

import com.mitbook.common.RedisUtil;
import com.mitbook.prefix.PrefixKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pengzhengfa
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Application.class)
public class Helloworld {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test() {
        PrefixKey pk = PrefixKey.with(3600, "pengzhengf");
        redisUtil.set(pk, "a", false, false);
        redisUtil.lock("a");
        redisUtil.unlock("a");
        String result = redisUtil.get(pk, "a", String.class);
        System.out.println(result);
    }
}
