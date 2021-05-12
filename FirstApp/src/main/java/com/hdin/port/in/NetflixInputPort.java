package com.hdin.port.in;

import com.hdin.exception.ApiException;
import com.hdin.model.EntryFilter;
import com.hdin.model.NetflixShows;
import com.hdin.model.RequestFilter;
import com.hdin.model.TvShowsRequest;

import java.io.FileInputStream;

public interface NetflixInputPort {
    public void parseNetflixData(FileInputStream inputStream) throws ApiException;
    public void filterData(String rowEntry);
    public void retriveFilterData(EntryFilter entryFilter,String startDate,String endData);
    public NetflixShows getTvShowsDetail(RequestFilter requestFilter, TvShowsRequest request);
}
