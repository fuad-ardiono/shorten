package com.fuad.shorten.scheduler;

import com.fuad.shorten.shared.utils.RedisSetUtil;
import com.fuad.shorten.shared.utils.ShortCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ShortCodeReservationScheduler {
    @Autowired
    ShortCodeUtil shortCodeUtil;

    @Scheduled(cron = "0 */5 * * * *")
    public void reserveShortCode() {
        shortCodeUtil.reserveSortCode();
    }
}
