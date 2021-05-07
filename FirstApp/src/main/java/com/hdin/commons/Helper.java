package com.hdin.commons;

import com.hdin.exception.ApiException;
import com.hdin.model.EntryFilter;
import com.hdin.model.NetflixShow;
import com.hdin.model.NetflixShows;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Helper {

    public static NetflixShow prepareNetflixShowObject(String entry) throws Exception{
        String[] values = entry.split(",");
        NetflixShow netflixShow = null;
        if (values.length == 12)
            netflixShow = new NetflixShow(values[0], values[1], values[2], values[3], values[4], values[5], dateFormater(values[6]), values[7], values[8], values[9], values[10], values[11]);
        return netflixShow;
    }

    public static EntryFilter requestIdentifier(NetflixShow netflixShow) {
        if (netflixShow.getType().equals(CommonConstants.TYPE_TV_SHOW)) {
            return EntryFilter.TV_SHOW;
        }

        if (netflixShow.getListedIn().contains(CommonConstants.HORROR_MOVIE)) {
            return EntryFilter.HORROR_MOVIE;
        }

        if (netflixShow.getType().equals(CommonConstants.TYPE_MOVIE) && netflixShow.getCountry().equals(CommonConstants.COUNTRY_INDIA)) {
            return EntryFilter.INDIAN_MOVE;
        }

        return EntryFilter.DEFAULT;
    }

    public static Date dateFormater(String inputDate) throws ParseException {

        inputDate = inputDate.replaceAll("#", ",");
        inputDate = inputDate.trim();
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = format.parse(inputDate);

        return date;
    }

    public static NetflixShows prepareNetflixShowsObjects(List<NetflixShow> list, String startDate, String endDate) {
        NetflixShows netflixShows = null;
        try {
            Date strDate = Helper.dateFormater(startDate);
            Date enDate = Helper.dateFormater(endDate);
            list.sort(Comparator.comparing(data -> data.getDateAdded()));
            List<NetflixShow> filterdList = list.stream().filter(data -> data.getDateAdded().after(strDate) && data.getDateAdded().before(enDate)).collect(Collectors.toList());
            netflixShows = new NetflixShows();
            netflixShows.setShowList(filterdList);
        } catch (Exception exp) {

        }
        return netflixShows;
    }
}