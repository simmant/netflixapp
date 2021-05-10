package com.hdin.port.in;

import com.hdin.exception.ApiException;
import com.hdin.model.EntryFilter;
import com.hdin.model.NetflixShows;
import com.hdin.model.RequestFilter;
import com.hdin.model.TvShowsRequest;

public interface NetflixInputPort {
    public void parseNetflixData(String filePath) throws ApiException;
    public void filterData(String rowEntry);
    public void retriveFilterData(EntryFilter entryFilter,String startDate,String endData);
    public NetflixShows getTvShowsDetail(RequestFilter requestFilter, TvShowsRequest request);
}
