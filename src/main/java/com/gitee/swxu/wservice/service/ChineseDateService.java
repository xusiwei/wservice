package com.gitee.swxu.wservice.service;

import com.alibaba.fastjson.JSON;
import com.gitee.swxu.wservice.bean.ChineseDate;
import com.gitee.swxu.wservice.utils.RedisHashAccessor;
import com.gitee.swxu.wservice.utils.TianApiClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChineseDateService {
    private static final Log log = LogFactory.getLog(ChineseDateService.class);
    private static final String CN_DATE_HASH_CACHE = "cn_date";

    @Autowired
    private RedisHashAccessor redisOp;

    public ChineseDate query(String dateStr) {
        // check in cached or not
        ChineseDate cachedDateInfo = getCachedDateInfo(dateStr);
        if (cachedDateInfo != null) {
            return cachedDateInfo;
        }

        synchronized (this) {
            // double check for concurrent query
            ChineseDate doubleCheckInfo = getCachedDateInfo(dateStr);
            if (doubleCheckInfo != null) {
                return doubleCheckInfo;
            }

            // fetch info from upstream service provider
            TianApiClient apiClient = new TianApiClient();
            ChineseDate chineseDate = apiClient.requestChineseDateInfo(dateStr);

            // update cache
            redisOp.set(CN_DATE_HASH_CACHE, dateStr, JSON.toJSONString(chineseDate));
            return chineseDate;
        }
    }

    private ChineseDate getCachedDateInfo(String dateStr) {
        String cachedDateInfo = redisOp.get(CN_DATE_HASH_CACHE, dateStr);
        if (cachedDateInfo != null) {
            log.debug("get cached chinese date info: " + cachedDateInfo);
            ChineseDate paredDate = JSON.parseObject(cachedDateInfo, ChineseDate.class);
            if (paredDate != null) {
                log.info("return cached chinese date info: " + paredDate);
                return paredDate;
            }
        }
        return null;
    }
}
