package com.hdin.port.out;

import com.hdin.model.NetflixShow;
import com.hdin.model.NetflixShows;

import java.util.List;

public interface NetflixOutputPort {
    public NetflixShows tvShows(List<NetflixShow> list,String startDate,String endDate);
    public NetflixShows horrorMovies(List<NetflixShow> list,String startDate,String endDate);
    public NetflixShows indianMovies(List<NetflixShow> list,String startDate,String endDate);
}
