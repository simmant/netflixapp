package com.hdin.commons;

import com.hdin.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ValidationHelper {
    static Logger logger = LoggerFactory.getLogger(ValidationHelper.class);

    public static boolean isAuthPresent(HttpServletRequest requestDetails) {
        logger.info("ValidationHelper->isAuthPresent call started");
        boolean flag = false;
        flag = Objects.nonNull(requestDetails.getHeader("X-Auth-Token"));
        logger.info("ValidationHelper->isAuthPresent call end");
        return flag;
    }
}
