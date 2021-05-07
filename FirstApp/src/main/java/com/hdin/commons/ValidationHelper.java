package com.hdin.commons;

import com.hdin.exception.ApiException;

import java.util.Objects;

public class ValidationHelper {

    public static void isValidDate(String date) throws ApiException {
        if (Objects.isNull(date)) {
            throw new ApiException("Not a valid Date");
        }
    }

}
