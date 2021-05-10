package com.hdin.service;

import com.hdin.adapater.NetflixAdapter;
import com.hdin.commons.Helper;
import com.hdin.exception.ApiException;
import com.hdin.model.*;
import com.hdin.port.in.NetflixInputPort;
import com.hdin.port.out.NetflixOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.*;

public class NetflixShowUseCase implements NetflixInputPort {
    Logger logger = LoggerFactory.getLogger(NetflixShowUseCase.class);
    NetflixOutputPort netflixOutputPort;
    static List<NetflixShow> tvShows = new ArrayList<>();
    static List<NetflixShow> horrorMovies = new ArrayList<>();
    static List<NetflixShow> indianMovies = new ArrayList<>();

    @Override
    public void parseNetflixData(String filePath) throws ApiException {
        logger.info("NetflixShowUseCase->parseNetflixData call started.");
        try {
            FileInputStream fio = new FileInputStream(filePath);
            Scanner sc = new Scanner(fio);
            while (sc.hasNext()) {
                filterData(sc.nextLine());
            }
        } catch (Exception exp) {
            logger.error("NetflixShowUseCase->parseNetflixData error: " + exp.getMessage());
            throw new ApiException(exp.getMessage());
        }
        logger.info("NetflixShowUseCase->parseNetflixData call end.");
    }

    @Override
    public void filterData(String rowEntry) {
        logger.info("NetflixShowUseCase->filterData call started.");
        try {
            NetflixShow netflixShow = Helper.prepareNetflixShowObject(rowEntry);
            if (Objects.nonNull(netflixShow)) {
                switch (Helper.requestIdentifier(netflixShow)) {
                    case TV_SHOW:
                        logger.debug("NetflixShowUseCase->filterData inside TV_SHOW case.");
                        tvShows.add(netflixShow);
                        break;

                    case HORROR_MOVIE:
                        logger.debug("NetflixShowUseCase->filterData inside HORROR_MOVIE case.");
                        horrorMovies.add(netflixShow);
                        break;

                    case INDIAN_MOVE:
                        logger.debug("NetflixShowUseCase->filterData inside INDIAN_MOVE case.");
                        indianMovies.add(netflixShow);
                        break;
                }
            }
        } catch (Exception exp) {
            logger.error("NetflixShowUseCase->filterData error: " + exp.getMessage());
        }
        logger.info("NetflixShowUseCase->filterData call end.");
    }

    @Override
    public void retriveFilterData(EntryFilter entryFilter, String startDate, String endDate) {
        logger.info("NetflixShowUseCase->retriveFilterData call started.");
        netflixOutputPort = new NetflixAdapter();

        switch (entryFilter) {
            case TV_SHOW:
                logger.debug("NetflixShowUseCase->retriveFilterData inside TV_SHOW case.");
                netflixOutputPort.tvShows(tvShows, startDate, endDate).getShowList().forEach(data -> {
                    System.out.println(data.getShowId());
                });
                break;

            case HORROR_MOVIE:
                logger.debug("NetflixShowUseCase->retriveFilterData inside HORROR_MOVIE case.");
                netflixOutputPort.horrorMovies(horrorMovies, startDate, endDate).getShowList().forEach(data -> {
                    System.out.println(data.getShowId() + " " + data.getTitle() + " " + data.getRating());
                });
                break;

            case INDIAN_MOVE:
                logger.debug("NetflixShowUseCase->retriveFilterData inside INDIAN_MOVE case.");
                netflixOutputPort.indianMovies(indianMovies, startDate, endDate).getShowList().forEach(data -> {
                    System.out.println(data.getShowId() + " " + data.getTitle() + " " + data.getRating());
                });
                break;
        }
        logger.info("NetflixShowUseCase->retriveFilterData call end.");
    }

    @Override
    public NetflixShows getTvShowsDetail(RequestFilter requestFilter, TvShowsRequest resuest) {
        logger.info("NetflixShowUseCase->getTvShowsDetail call started.");

        NetflixShows netflixShows = new NetflixShows();
        netflixOutputPort = new NetflixAdapter();
        switch (requestFilter) {
            case COUNT:
                logger.debug("NetflixShowUseCase->getTvShowsDetail inside COUNT case.");
                netflixShows.setShowList(tvShows.subList(0, resuest.getCount()));
                break;
            case TYPE:
                logger.debug("NetflixShowUseCase->getTvShowsDetail inside TYPE case.");
                netflixShows.setShowList(horrorMovies);
                break;

            case COUNTRY:
                logger.debug("NetflixShowUseCase->getTvShowsDetail inside COUNTRY case.");
                netflixShows.setShowList(indianMovies);
                break;

            case DATE_RANGE:
                logger.debug("NetflixShowUseCase->getTvShowsDetail inside DATE_RANGE case.");
                netflixShows = netflixOutputPort.tvShows(tvShows, resuest.getStartDate(), resuest.getEndDate());
                break;
        }
        logger.info("NetflixShowUseCase->getTvShowsDetail call end");
        return netflixShows;
    }
}