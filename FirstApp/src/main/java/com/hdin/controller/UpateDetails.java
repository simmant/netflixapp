package com.hdin.controller;

import com.hdin.commons.CommonConstants;
import com.hdin.comp.NetFlxComp;
import com.hdin.model.NetflixShow;
import com.hdin.model.NfApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UpateDetails {

    @Autowired
    private NetFlxComp netFlxComp;

    @RequestMapping(value = "/addshow", method = RequestMethod.POST)
    public @ResponseBody
    NfApiResponse<String> saveEntry(@RequestBody NetflixShow show) {
        NfApiResponse<String> apiResponse = new NfApiResponse<>();
        apiResponse.setResponse(netFlxComp.saveShows(show));
        apiResponse.setMessage(CommonConstants.SAVE_DATA_MSG);
        return apiResponse;
    }

}
