package top.hxyac.chatbot.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BaseCache {


//初始容量，最大值，并发数，存活时间，统计缓存命中率
    private Cache<String,Object> tenMinuteCache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .maximumSize(100)
            .concurrencyLevel(4)
            .expireAfterWrite(600, TimeUnit.MINUTES)
            .recordStats()
            .build();

    private Cache<String,Object> oneHourCache = CacheBuilder.newBuilder()
            .initialCapacity(2)
            .maximumSize(4)
            .concurrencyLevel(4)
            .expireAfterWrite(3600, TimeUnit.MINUTES)
            .recordStats()
            .build();

    public Cache<String, Object> getTenMinuteCache() {
        return tenMinuteCache;
    }

    public void setTenMinuteCache(Cache<String, Object> tenMinuteCache) {
        this.tenMinuteCache = tenMinuteCache;
    }

    public Cache<String, Object> getOneHourCache() {
        return oneHourCache;
    }

    public void setOneHourCache(Cache<String, Object> oneHourCache) {
        this.oneHourCache = oneHourCache;
    }
}
