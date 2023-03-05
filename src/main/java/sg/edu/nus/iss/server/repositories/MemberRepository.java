package sg.edu.nus.iss.server.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String,String> redisTemplate;

    // Save payload to Redis Database
    public void save(String key, String payload) {
        ValueOperations<String,String> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, payload);
    }

    // Get payload from Redis Database
    public Optional<String> get(String key) {
        ValueOperations<String,String> valueOps = redisTemplate.opsForValue();
        String payload = valueOps.get(key);
        if (null == payload) {
            return Optional.empty();
        }
        return Optional.of(payload);
    }
}
