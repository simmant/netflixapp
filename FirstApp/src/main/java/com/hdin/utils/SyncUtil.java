package com.hdin.utils;

import com.hdin.commons.Helper;
import com.hdin.model.NetflixShow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Scanner;

public class SyncUtil implements Runnable {
    Logger logger = LoggerFactory.getLogger(SyncUtil.class);
    String filePath;

    public SyncUtil(String filePath) {
        this.filePath = filePath;
    }

    private void showSync(String path) throws Exception {

        File file = new File(path);
        FileInputStream fio = new FileInputStream(path);
        Scanner scanner = new Scanner(fio);
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/addshow";
        URI uri = new URI(baseUrl);
        long initalSize = file.length();
        logger.info("initial file size :"+initalSize);
        while (true) {
            Thread.sleep(60000);
            long size = file.length();
            if (initalSize != size) {
                logger.info("File size change :");
                initalSize = size;
                scanner.nextLine();
                NetflixShow showNew = Helper.prepareNetflixShowObject(scanner.nextLine());
                ResponseEntity<String> result = restTemplate.postForEntity(uri, showNew, String.class);
                logger.info("Response:"+result);
            }

        }
    }

    @Override
    public void run() {
        try {
            showSync(filePath);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
