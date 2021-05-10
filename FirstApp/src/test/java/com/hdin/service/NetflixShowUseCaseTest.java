package com.hdin.service;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.hdin.adapater.NetflixAdapter;
import com.hdin.exception.ApiException;
import com.hdin.model.*;
import com.hdin.port.in.NetflixInputPort;
import com.hdin.port.out.NetflixOutputPort;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@SpringBootTest
public class NetflixShowUseCaseTest {
    private String sampleEntry = "s1,TV Show,3%,,João Miguel, Bianca Comparato, Michel Gomes, Rodolfo Valente, Vaneza Oliveira, Rafael Lozano, Viviane Porto, Mel Fronckowiak, Sergio Mamberti, Zezé Motta, Celso Frateschi,Brazil,August 14, 2017,2017,TV-MA,4 Seasons,International TV Shows, TV Dramas, TV Sci-Fi & Fantasy,In a future where the elite inhabit an island paradise far from the crowded slums, you get one chance to join the 3% saved from squalor.,";



    @Test
    public void testParseNetflixData() throws Exception {

        LogCaptor logCaptor = LogCaptor.forClass(NetflixShowUseCase.class);

        FileInputStream fio = Mockito.mock(FileInputStream.class);
        Scanner sc = Mockito.mock(Scanner.class);

        Mockito.when(sc.hasNext()).thenReturn(true);
        Mockito.when(sc.nextLine()).thenReturn(sampleEntry);

        NetflixInputPort netflixShowUseCase = new NetflixShowUseCase();
        netflixShowUseCase.parseNetflixData("path/to/file");

        Assertions.assertTrue(logCaptor.getInfoLogs().contains("NetflixShowUseCase->parseNetflixData call end."));
    }

    @Test
    public void testParseNetflixData_Exception() throws Exception {
        NetflixInputPort netflixShowUseCase = new NetflixShowUseCase();
        Exception exp = Assertions.assertThrows(ApiException.class, () -> netflixShowUseCase.parseNetflixData(""));
        Assertions.assertTrue(Objects.nonNull(exp));
    }

    @Test
    public void testFilterData() {
        LogCaptor logCaptor = LogCaptor.forClass(NetflixShowUseCase.class);
        NetflixInputPort netflixShowUseCase = new NetflixShowUseCase();
        netflixShowUseCase.filterData(sampleEntry);
        Assertions.assertTrue(logCaptor.getInfoLogs().contains("NetflixShowUseCase->filterData call end."));
    }

    @Test
    public void testFilterData_Exception() {
        LogCaptor logCaptor = LogCaptor.forClass(NetflixShowUseCase.class);
        NetflixInputPort netflixShowUseCase = new NetflixShowUseCase();
        netflixShowUseCase.filterData(null);
        Assertions.assertTrue(logCaptor.getErrorLogs().stream().anyMatch(data->data.contains("NetflixShowUseCase->filterData error")));
    }

    @Test
    public void testRetriveFilterData(){
        LogCaptor logCaptor = LogCaptor.forClass(NetflixShowUseCase.class);

        NetflixShow ntf = new NetflixShow("test","test","test","test","test","test","test","test","test","test","test","test");
        List<NetflixShow> ntfLst = new ArrayList<>();
        NetflixShows shws = new NetflixShows();
        shws.setShowList(ntfLst);

        NetflixOutputPort netflixOutputPort = Mockito.mock(NetflixOutputPort.class);
        Mockito.when(netflixOutputPort.tvShows(Mockito.anyList(),Mockito.anyString(),Mockito.anyString())).thenReturn(shws);

        NetflixInputPort netflixShowUseCase = new NetflixShowUseCase();
        netflixShowUseCase.retriveFilterData(EntryFilter.TV_SHOW,"May 10, 2016","May 10, 2019");

        Assertions.assertTrue(logCaptor.getDebugLogs().contains("NetflixShowUseCase->retriveFilterData inside TV_SHOW case.") && logCaptor.getInfoLogs().contains("NetflixShowUseCase->retriveFilterData call end."));
    }

    @Test
    public void testGetTvShowsDetail(){
        NetflixShow ntf = new NetflixShow("test","test","test","test","test","test","test","test","test","test","test","test");
        List<NetflixShow> ntfLst = new ArrayList<>();
        NetflixShows shws = new NetflixShows();
        shws.setShowList(ntfLst);

        NetflixOutputPort netflixOutputPort = Mockito.mock(NetflixOutputPort.class);
        Mockito.when(netflixOutputPort.tvShows(Mockito.anyList(),Mockito.anyString(),Mockito.anyString())).thenReturn(shws);

        NetflixInputPort netflixShowUseCase = new NetflixShowUseCase();
        TvShowsRequest request = new TvShowsRequest();
        request.setStartDate("May 10, 2016");
        request.setEndDate("May 10, 2019");
        NetflixShows response = netflixShowUseCase.getTvShowsDetail(RequestFilter.DATE_RANGE, request);

        Assertions.assertEquals(1 ,response.getShowList().size());
    }
}
