package com.hdin.service;

import com.hdin.adapater.NetflixAdapter;
import com.hdin.exception.ApiException;
import com.hdin.model.*;
import com.hdin.port.in.NetflixInputPort;
import com.hdin.port.out.NetflixOutputPort;

import nl.altindag.log.LogCaptor;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@SpringBootTest
public class NetflixShowUseCaseTest {
    ClassLoader classLoader = getClass().getClassLoader();

    private String sampleEntry = "s1,TV Show,3%,,João Miguel, Bianca Comparato, Michel Gomes, Rodolfo Valente, Vaneza Oliveira, Rafael Lozano, Viviane Porto, Mel Fronckowiak, Sergio Mamberti, Zezé Motta, Celso Frateschi,Brazil,August 14, 2017,2017,TV-MA,4 Seasons,International TV Shows, TV Dramas, TV Sci-Fi & Fantasy,In a future where the elite inhabit an island paradise far from the crowded slums, you get one chance to join the 3% saved from squalor.,";

    NetflixShowUseCase netflixShowUseCase = new NetflixShowUseCase();

    @Mock
    NetflixOutputPort netflixOutputPort;

    @Test
    public void testParseNetflixData() throws Exception {

        LogCaptor logCaptor = LogCaptor.forClass(NetflixShowUseCase.class);

        FileInputStream fio = Mockito.mock(FileInputStream.class);
        Scanner sc = Mockito.mock(Scanner.class);

        Mockito.when(sc.hasNext()).thenReturn(true);
        Mockito.when(sc.nextLine()).thenReturn(sampleEntry);


        netflixShowUseCase.parseNetflixData(new FileInputStream(classLoader.getResource("test.csv").getFile()));

        Assertions.assertTrue(logCaptor.getInfoLogs().contains("NetflixShowUseCase->parseNetflixData call end."));
    }

    @Test
    public void testParseNetflixData_Exception() throws Exception {

        Exception exp = Assertions.assertThrows(ApiException.class, () -> netflixShowUseCase.parseNetflixData(null));
        Assertions.assertTrue(Objects.nonNull(exp));
    }

    @Test
    public void testFilterData() {
        LogCaptor logCaptor = LogCaptor.forClass(NetflixShowUseCase.class);
        netflixShowUseCase.filterData(sampleEntry);
        Assertions.assertTrue(logCaptor.getInfoLogs().contains("NetflixShowUseCase->filterData call end."));
    }

    @Test
    public void testFilterData_Exception() {
        LogCaptor logCaptor = LogCaptor.forClass(NetflixShowUseCase.class);
        netflixShowUseCase.filterData(null);
        Assertions.assertTrue(logCaptor.getErrorLogs().stream().anyMatch(data -> data.contains("NetflixShowUseCase->filterData error")));
    }

}