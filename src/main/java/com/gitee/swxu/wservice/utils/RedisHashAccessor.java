package com.gitee.swxu.wservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RedisHashAccessor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String get(String key, String field) {
        Object obj = redisTemplate.opsForHash().get(key, field);
        return obj == null ? null : obj.toString();
    }

    public void set(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public void del(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    public Map<String, String> getAll(String key) {
        return redisTemplate.execute((RedisCallback<Map<String, String>>) con -> {
            Map<byte[], byte[]> result = con.hGetAll(key.getBytes());
            if (CollectionUtils.isEmpty(result)) {
                return new HashMap<>(0);
            }

            Map<String, String> ans = new HashMap<>(result.size());
            for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
                ans.put(new String(entry.getKey()), new String(entry.getValue()));
            }
            return ans;
        });
    }
}
