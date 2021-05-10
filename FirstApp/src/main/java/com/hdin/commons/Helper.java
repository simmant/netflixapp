package com.hdin.commons;

import com.hdin.exception.ApiException;
import com.hdin.model.EntryFilter;
import com.hdin.model.NetflixShow;
import com.hdin.model.NetflixShows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Helper {

    static Logger logger = LoggerFactory.getLogger(Helper.class);

    public static NetflixShow prepareNetflixShowObject(String entry) throws Exception {
        logger.info("Helper->prepareNetflixShowObject call started");
        String data = entry.replace(", ", "#");
        String[] values = data.split(",");
        List<String> finalValues = Arrays.asList(values).stream().map(d -> d.replace("#", ", ")).collect(Collectors.toList());
        NetflixShow netflixShow = null;
        if (finalValues.size() == 12) {
            logger.debug("Helper->prepareNetflixShowObject preparing NetflixShow object.");
            netflixShow = new NetflixShow(finalValues.get(0), finalValues.get(1), finalValues.get(2), finalValues.get(3), finalValues.get(4), finalValues.get(5), finalValues.get(6), finalValues.get(7), finalValues.get(8), finalValues.get(9), finalValues.get(10), finalValues.get(11));
        }
        logger.info("Helper->prepareNetflixShowObject call end");
        return netflixShow;
    }

    public static EntryFilter requestIdentifier(NetflixShow netflixShow) {
        logger.info("Helper->requestIdentifier call started");

        if (netflixShow.getType().equals(CommonConstants.TYPE_TV_SHOW)) {
            logger.debug("Helper->requestIdentifier TYPE_TV_SHOW case");
            return EntryFilter.TV_SHOW;
        }

        if (netflixShow.getListedIn().contains(CommonConstants.HORROR_MOVIE)) {
            logger.debug("Helper->requestIdentifier HORROR_MOVIE case");
            return EntryFilter.HORROR_MOVIE;
        }

        if (netflixShow.getType().equals(CommonConstants.TYPE_MOVIE) && netflixShow.getCountry().equals(CommonConstants.COUNTRY_INDIA)) {
            logger.debug("Helper->requestIdentifier TYPE_MOVIE case");
            return EntryFilter.INDIAN_MOVE;
        }

        logger.info("Helper->requestIdentifier call end");
        return EntryFilter.DEFAULT;
    }

    public static Date dateFormater(String inputDate) {
        logger.info("Helper->dateFormater call started");
        try {
            inputDate = inputDate.trim();
            inputDate = inputDate.replace("\"", "");
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = format.parse(inputDate);
            logger.info("Helper->dateFormater call end");
            return date;
        } catch (ParseException exp) {
            logger.error("Helper->dateFormater exception :"+exp.getMessage());
            return new Date();
        }
    }

    public static NetflixShows prepareNetflixShowsObjects(List<NetflixShow> list, String startDate, String endDate) {
        logger.info("Helper->prepareNetflixShowsObjects call started");
        NetflixShows netflixShows = null;
        try {
            Date strDate = Helper.dateFormater(startDate);
            Date enDate = Helper.dateFormater(endDate);
            list.stream().filter(data -> data.getDateAdded() != null).collect(Collectors.toList()).sort(Comparator.comparing(data -> data.getDateAdded()));
            List<NetflixShow> filterdList = list.stream().filter(data -> dateFormater(data.getDateAdded()).after(strDate) && dateFormater(data.getDateAdded()).before(enDate)).collect(Collectors.toList());
            netflixShows = new NetflixShows();
            netflixShows.setShowList(filterdList);
        } catch (Exception exp) {
            logger.error("Helper->prepareNetflixShowsObjects exception: "+exp.getMessage());
        }
        logger.info("Helper->prepareNetflixShowsObjects call end");
        return netflixShows;
    }

}