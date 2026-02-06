package com.example.monitor.scheduler;

import com.example.monitor.service.GitHubMonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GitHubRefreshScheduler {


    @Scheduled(cron = "${app.scheduler.refresh-cron:0 13 15 * * *}")
    public void scheduleFixedRateTask() {
        log.info("Planlama basladı");

    }
}