package com.hdin.comp;

import com.hdin.db.repo.NetflixShowRepository;
import com.hdin.model.NetflixShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

@Service("netFlxComp")
@Transactional
public class NetFlxCompImpl implements NetFlxComp {
    @Autowired
    NetflixShowRepository netflixShowRepository;

    @Override
    public String saveShows(NetflixShow show) {

        String result = "";
        com.hdin.db.model.NetflixShow netflixShow = new com.hdin.db.model.NetflixShow();

        netflixShow.setShowId(show.getShowId());
        netflixShow.setCast(show.getCast());
        netflixShow.setCountry(show.getCountry());
        netflixShow.setDescription(show.getDescription());
        netflixShow.setDirector(show.getDirector());
        netflixShow.setDateAdded(show.getDateAdded());
        netflixShow.setDuration(show.getDuration());
        netflixShow.setListedIn(show.getListedIn());
        netflixShow.setRating(show.getRating());
        netflixShow.setReleaseYear(show.getReleaseYear());
        netflixShow.setTitle(show.getTitle());
        netflixShow.setType(show.getType());

        com.hdin.db.model.NetflixShow response = netflixShowRepository.save(netflixShow);
        if(Objects.nonNull(response)){
            result = "SAVED";
        }

        return result;
    }


}
