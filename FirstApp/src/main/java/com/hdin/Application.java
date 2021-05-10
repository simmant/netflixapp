package com.hdin;

import com.hdin.service.NetflixShowUseCase;
import com.hdin.utils.SyncUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    static{
        try {
            NetflixShowUseCase netflixShowUseCase = new NetflixShowUseCase();
            netflixShowUseCase.parseNetflixData("/path/to/csvfile");
            SyncUtil syncUtil = new SyncUtil("/path/to/csvfile");
            Thread th = new Thread(syncUtil);
            th.start();
        }catch (Exception exception){

        }
    }

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
