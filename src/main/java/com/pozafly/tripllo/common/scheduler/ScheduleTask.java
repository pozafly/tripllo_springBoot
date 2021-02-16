package com.pozafly.tripllo.common.scheduler;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class ScheduleTask {

    @Autowired
    private TestUserJob testUserJob;

//        @Scheduled(cron = "1-30/3 * * * * *", zone = "Asia/Seoul")
    @Scheduled(cron = "0 0 7-23/2 * * *", zone = "Asia/Seoul")
    public void excuteTask() {
        log.info("스케줄러 시작 시간 : {}", new Date());

        try {
            testUserJob.excute();
        } catch(Exception e) {
            e.printStackTrace();
        }

        log.info("스케줄러 완료");
    }
}
