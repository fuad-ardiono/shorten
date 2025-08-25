package com.fuad.shorten.shared.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class RedisSetUtil {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void addToSet(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    public Set<Object> getSetMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Optional<Boolean> isMember(String key, Object value) {
        return Optional.ofNullable(redisTemplate.opsForSet().isMember(key, value));
    }

    public void removeFromSet(String key, Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    public Optional<Long> getSetSize(String key) {
        return Optional.ofNullable(redisTemplate.opsForSet().size(key));
    }

    public Object popFromSet(String key) {
        return redisTemplate.opsForSet().pop(key);
    }
}
