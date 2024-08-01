package pl.damian.repositoryfetcher.service;

import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CacheService {

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRate = 1800000L)
    public void evict() {
        Objects.requireNonNull(cacheManager.getCache("UserRepositoriesWithBranches")).clear();
    }

}