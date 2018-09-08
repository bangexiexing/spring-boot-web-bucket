package bucket.component.cache;

import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 19:00 2018/9/8
 */
public class MyCacheManager extends AbstractCacheManager {
    private Collection<? extends MyCache> caches;
    /**
     * Specify the collection of Cache instances to use for this CacheManager.
     */
    public void setCaches(Collection<? extends MyCache> caches) {
        this.caches = caches;
    }

    @Override
    protected Collection<? extends MyCache> loadCaches() {
        return this.caches;
    }

}