//package com.hdin.main;
//
//import com.hdin.service.NetflixShowUseCase;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
//
//import java.util.Scanner;
//
//@SpringBootApplication
//@ComponentScan(basePackages = {"com.hdin.controller","com.hdin.db.model","com.hdin.db.repo","com.hdin.comp"})
//public class Application {
//    static{
//        try {
//            NetflixShowUseCase netflixShowUseCase = new NetflixShowUseCase();
//            netflixShowUseCase.parseNetflixData("/home/ritamaa/Downloads/netflix_titles.csv");
//        }catch (Exception exception){
//
//        }
//    }
//
//    public static void main(String[] args){
//        SpringApplication.run(Application.class, args);
//    }
//}
