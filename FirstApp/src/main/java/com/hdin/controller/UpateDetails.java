package com.hdin.controller;

import com.hdin.comp.NetFlxComp;
import com.hdin.model.NetflixShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UpateDetails {

    @Autowired
    private NetFlxComp netFlxComp;

    @RequestMapping(value="/addshow",method = RequestMethod.POST)
    public @ResponseBody String saveEntry(@RequestBody NetflixShow show){
        return netFlxComp.saveShows(show);
    }

}
