package com.mitbook.limit;

import com.alibaba.fastjson.JSON;
import com.mitbook.common.RedisUtil;
import com.mitbook.prefix.PrefixKey;
import com.mitbook.result.CodeMsg;
import com.mitbook.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author pengzhengfa
 */
public class LimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            RedisLimit accessLimit = hm.getMethodAnnotation(RedisLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            String key = request.getRequestURI();
            PrefixKey pk = PrefixKey.with(seconds, "limit");
            Integer count = redisUtil.get(pk, key, Integer.class);
            if (count == null) {
                redisUtil.set(pk, key, 1, false);
            } else if (count < maxCount) {
                redisUtil.incr(pk, key);
            } else {
                render(response, CodeMsg.LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
