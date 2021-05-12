package com.hdin.controller;

import com.hdin.commons.CommonConstants;
import com.hdin.commons.ValidationHelper;
import com.hdin.model.NetflixShows;
import com.hdin.model.NfApiResponse;
import com.hdin.model.RequestFilter;
import com.hdin.model.TvShowsRequest;
import com.hdin.port.in.NetflixInputPort;
import com.hdin.service.NetflixShowUseCase;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ShowDetailController {

    @Autowired
    HttpServletRequest requestDetais;

    NetflixInputPort netflixInputPort = new NetflixShowUseCase();

    @RequestMapping(value = "/tvshows", method = RequestMethod.GET)
    public ResponseEntity<NfApiResponse> getTvShowsCounts(@RequestParam Map<String, String> params) {

        long startTime = System.currentTimeMillis();
        Map.Entry<String, String> param = params.entrySet().iterator().next();
        NetflixShows shows = null;
        TvShowsRequest tvShowsRequest = new TvShowsRequest();
        NfApiResponse<NetflixShows> response = new NfApiResponse<>();

        if (!ValidationHelper.isAuthPresent(requestDetais)) {
            response.setMessage(CommonConstants.UNAUTHORIZED_USER);
            response.setResponse(shows);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        switch (param.getKey()) {
            case "count":
                tvShowsRequest.setCount(Integer.parseInt(param.getValue()));
                shows = netflixInputPort.getTvShowsDetail(RequestFilter.COUNT, tvShowsRequest);
                break;
            case "country":
                tvShowsRequest.setCountry(param.getValue());
                shows = netflixInputPort.getTvShowsDetail(RequestFilter.COUNTRY, tvShowsRequest);
                break;

            case "movieType":
                tvShowsRequest.setType(param.getValue());
                shows = netflixInputPort.getTvShowsDetail(RequestFilter.TYPE, tvShowsRequest);
                break;

            case "startDate":
                tvShowsRequest.setStartDate(params.get("startDate"));
                tvShowsRequest.setEndDate(params.get("endDate"));

                shows = netflixInputPort.getTvShowsDetail(RequestFilter.DATE_RANGE, tvShowsRequest);
                break;
        }

        long end = System.currentTimeMillis();
        long total = end - startTime;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TIME-TO-EXECUTE", total + " MS");

        response.setMessage(CommonConstants.SHOW_DATA_MSG);
        response.setResponse(shows);
        return ResponseEntity.accepted().headers(headers).body(response);
    }


}