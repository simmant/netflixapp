package com.hdin.adapater;

import com.hdin.commons.Helper;
import com.hdin.model.NetflixShows;
import com.hdin.model.NetflixShow;
import com.hdin.port.out.NetflixOutputPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetflixAdapter implements NetflixOutputPort {
    @Override
    public NetflixShows tvShows(List<NetflixShow> list, String startDate, String endDate) {
        return Helper.prepareNetflixShowsObjects(list, startDate, endDate);
    }

    @Override
    public NetflixShows horrorMovies(List<NetflixShow> list, String startDate, String endDate) {
        return Helper.prepareNetflixShowsObjects(list, startDate, endDate);
    }

    @Override
    public NetflixShows indianMovies(List<NetflixShow> list, String startDate, String endDate) {
        return Helper.prepareNetflixShowsObjects(list, startDate, endDate);
    }
}
