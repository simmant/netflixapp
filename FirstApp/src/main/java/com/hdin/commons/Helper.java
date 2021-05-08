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

    public static NetflixShow prepareNetflixShowObject(String entry) throws Exception {
        String data = entry.replace(", ", "#");
        String[] values = data.split(",");
        List<String> finalValues = Arrays.asList(values).stream().map(d -> d.replace("#", ", ")).collect(Collectors.toList());
        NetflixShow netflixShow = null;
        if (finalValues.size() == 12)
            netflixShow = new NetflixShow(finalValues.get(0), finalValues.get(1), finalValues.get(2), finalValues.get(3), finalValues.get(4), finalValues.get(5), finalValues.get(6), finalValues.get(7), finalValues.get(8), finalValues.get(9), finalValues.get(10), finalValues.get(11));
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

    public static Date dateFormater(String inputDate){
        try {
            inputDate = inputDate.trim();
            inputDate = inputDate.replace("\"","");
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = format.parse(inputDate);
            return date;
        }catch(ParseException exp){
            return new Date();
        }
    }

    public static NetflixShows prepareNetflixShowsObjects(List<NetflixShow> list, String startDate, String endDate){
        NetflixShows netflixShows = null;
        try {
            Date strDate = Helper.dateFormater(startDate);
            Date enDate = Helper.dateFormater(endDate);
            list.stream().filter(data->data.getDateAdded()!=null).collect(Collectors.toList()).sort(Comparator.comparing(data -> data.getDateAdded()));
            List<NetflixShow> filterdList = list.stream().filter(data -> dateFormater(data.getDateAdded()).after(strDate) && dateFormater(data.getDateAdded()).before(enDate)).collect(Collectors.toList());
            netflixShows = new NetflixShows();
            netflixShows.setShowList(filterdList);
        } catch (Exception exp) {

        }
        return netflixShows;
    }



}