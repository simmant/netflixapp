package com.hdin.commons;

import com.hdin.model.EntryFilter;
import com.hdin.model.NetflixShow;
import com.hdin.model.NetflixShows;
import org.junit.jupiter.api.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelperTest {

    private static String sampleEntry = "s1,TV Show,3%,,João Miguel, Bianca Comparato, Michel Gomes, Rodolfo Valente, Vaneza Oliveira, Rafael Lozano, Viviane Porto, Mel Fronckowiak, Sergio Mamberti, Zezé Motta, Celso Frateschi,Brazil,August 14, 2017,2017,TV-MA,4 Seasons,International TV Shows, TV Dramas, TV Sci-Fi & Fantasy,In a future where the elite inhabit an island paradise far from the crowded slums, you get one chance to join the 3% saved from squalor.,";
    private static String sampleEntry2 = "s1,TV Show,3%,,João Miguel, Bianca Comparato, Michel Gomes, Rodolfo Valente, Vaneza Oliveira, Rafael Lozano, Viviane Porto, Mel Fronckowiak, Sergio Mamberti, Zezé Motta, Celso Frateschi,Brazil,August 14, 2019,2019,TV-MA,4 Seasons,International TV Shows, TV Dramas, TV Sci-Fi & Fantasy,In a future where the elite inhabit an island paradise far from the crowded slums, you get one chance to join the 3% saved from squalor.,";
    private static String sampleEntry3 = "s1,TV Show,3%,,João Miguel, Bianca Comparato, Michel Gomes, Rodolfo Valente, Vaneza Oliveira, Rafael Lozano, Viviane Porto, Mel Fronckowiak, Sergio Mamberti, Zezé Motta, Celso Frateschi,Brazil,August 14, 2021,2021,TV-MA,4 Seasons,International TV Shows, TV Dramas, TV Sci-Fi & Fantasy,In a future where the elite inhabit an island paradise far from the crowded slums, you get one chance to join the 3% saved from squalor.,";
    @Test
    public void testDateFormater() throws Exception {
        Date date = Helper.dateFormater("May 10, 2020");
        Assertions.assertTrue(date.toString().equals("Sun May 10 00:00:00 IST 2020"));
    }


    @Test
    public void testPrepareNetflixShowObject() throws Exception {
        NetflixShow netflixShow = Helper.prepareNetflixShowObject(sampleEntry);
        Assertions.assertEquals(netflixShow.getShowId(),"s1");
    }

    @Test
    public void testRequestIdentifier() throws Exception {
        NetflixShow netflixShow = Helper.prepareNetflixShowObject(sampleEntry);
        EntryFilter entryFilter = Helper.requestIdentifier(netflixShow);
        Assertions.assertEquals(entryFilter,EntryFilter.TV_SHOW);
    }

    @Test
    public void testPrepareNetflixShowsObjects() throws Exception {
        NetflixShow netflixShow = Helper.prepareNetflixShowObject(sampleEntry);
        NetflixShow netflixShow2 = Helper.prepareNetflixShowObject(sampleEntry2);
        NetflixShow netflixShow3 = Helper.prepareNetflixShowObject(sampleEntry3);

        List<NetflixShow> listNetflixShow = new ArrayList<>();
        listNetflixShow.add(netflixShow);
        listNetflixShow.add(netflixShow2);
        listNetflixShow.add(netflixShow3);

        NetflixShows shows = Helper.prepareNetflixShowsObjects(listNetflixShow,"May 10, 2016","May 10, 2020");

        Assertions.assertEquals(shows.getShowList().size(),2);
    }
}
