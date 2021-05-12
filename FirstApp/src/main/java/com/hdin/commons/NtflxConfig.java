package com.hdin.commons;

import com.hdin.port.in.NetflixInputPort;
import com.hdin.utils.SyncUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

@Component
public class NtflxConfig {
    static Logger logger = LoggerFactory.getLogger(NtflxConfig.class);

    @Autowired
    NetflixInputPort netflixInputPort;

    Resource resource = new ClassPathResource("netflix_titles.csv");

    @PostConstruct
    public void loadNtfConfig() {
        logger.info("NtflxConfig->loadNtflxConfig call started.");
        try {
            File file = new File(System.getProperty("user.dir") + "/data.csv");
            netflixInputPort.parseNetflixData(new FileInputStream(file));
            SyncUtil syncUtil = new SyncUtil(file);
            Thread thread =  new Thread(syncUtil);
            thread.start();
        } catch (Exception exp) {
            logger.error("NtflxConfig->loadNtflxConfig exception: " + exp.getMessage());
        }
        logger.info("NtflxConfig->loadNtflxConfig call end.");
    }
}
