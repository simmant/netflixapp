package com.hdin.port.in;

import com.hdin.exception.ApiException;
import com.hdin.model.EntryFilter;

import java.io.FileNotFoundException;

public interface NetflixInputPort {
    public void parseNetflixData(String filePath) throws ApiException;
    public void filterData(String rowEntry);
    public void retriveFilterData(EntryFilter entryFilter,String startDate,String endData);
}
