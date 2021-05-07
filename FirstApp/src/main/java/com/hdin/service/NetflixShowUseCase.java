package com.hdin.service;

import com.hdin.adapater.NetflixAdapter;
import com.hdin.commons.Helper;
import com.hdin.exception.ApiException;
import com.hdin.model.EntryFilter;
import com.hdin.model.NetflixShow;
import com.hdin.port.in.NetflixInputPort;
import com.hdin.port.out.NetflixOutputPort;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class NetflixShowUseCase implements NetflixInputPort {
    NetflixOutputPort netflixOutputPort;
    List<NetflixShow> tvShows = new ArrayList<>();
    List<NetflixShow> horrorMovies = new ArrayList<>();
    List<NetflixShow> indianMovies = new ArrayList<>();

    @Override
    public void parseNetflixData(String filePath) throws ApiException {
        try {
            FileInputStream fio = new FileInputStream(filePath);
            Scanner sc = new Scanner(fio);
            while (sc.hasNext()) {
                filterData(sc.nextLine());
            }
        }catch(Exception exp){
            throw new ApiException(exp.getMessage());
        }
    }

    @Override
    public void filterData(String rowEntry) {
        try {
            NetflixShow netflixShow = Helper.prepareNetflixShowObject(rowEntry);
            if (Objects.nonNull(netflixShow)) {
                switch (Helper.requestIdentifier(netflixShow)) {
                    case TV_SHOW:
                        tvShows.add(netflixShow);
                        break;

                    case HORROR_MOVIE:
                        horrorMovies.add(netflixShow);
                        break;

                    case INDIAN_MOVE:
                        indianMovies.add(netflixShow);
                        break;
                }
            }
        }catch(Exception exp){
            exp.printStackTrace();
        }
    }

    @Override
    public void retriveFilterData(EntryFilter entryFilter, String startDate, String endDate) {
        netflixOutputPort = new NetflixAdapter();

        switch (entryFilter) {
            case TV_SHOW:
                System.out.println(tvShows.size());
                netflixOutputPort.tvShows(tvShows, startDate, endDate).getShowList().forEach(data -> {
                    System.out.println(data.getShowId());
                });
                break;

            case HORROR_MOVIE:
                netflixOutputPort.horrorMovies(horrorMovies, startDate, endDate).getShowList().forEach(data -> {
                    System.out.println(data.getShowId()+" "+data.getTitle()+" "+data.getRating());
                });
                break;

            case INDIAN_MOVE:
                netflixOutputPort.indianMovies(indianMovies, startDate, endDate).getShowList().forEach(data -> {
                    System.out.println(data.getShowId()+" "+data.getTitle()+" "+data.getRating());
                });
                break;
        }

    }
}
