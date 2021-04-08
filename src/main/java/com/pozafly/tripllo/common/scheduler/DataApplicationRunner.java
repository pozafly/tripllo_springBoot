package com.pozafly.tripllo.common.scheduler;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class DataApplicationRunner implements ApplicationRunner {

    @Autowired
    private TestUserJob testUserJob;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        log.info("스케줄러 시작 시간 : {}", new Date());
//
//        try {
//            testUserJob.excute();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//        log.info("스케줄러 완료");
    }
}
