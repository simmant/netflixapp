package com.hdin.main;

import com.hdin.commons.ValidationHelper;
import com.hdin.model.EntryFilter;
import com.hdin.service.NetflixShowUseCase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMain {

    public static void main(String[] arg) throws Exception {
        NetflixShowUseCase netflixShowUseCase = new NetflixShowUseCase();
        netflixShowUseCase.parseNetflixData("/home/ritamaa/Downloads/netflix_titles.csv");
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter start date");
        String startDate = sc.nextLine();
        

        System.out.println("Please enter end date");
        String endDate = sc.nextLine();


        while(true) {
            System.out.println("Press 1 for display all TV Shows");
            System.out.println("Press 2 for display all Horror Movies");
            System.out.println("Press 3 for display all movies availabe for India");
            System.out.println("Press 4 for close the program");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    netflixShowUseCase.retriveFilterData(EntryFilter.TV_SHOW, startDate, endDate);
                    break;

                case 2:
                    netflixShowUseCase.retriveFilterData(EntryFilter.HORROR_MOVIE,startDate,endDate);
                    break;

                case 3:
                    netflixShowUseCase.retriveFilterData(EntryFilter.INDIAN_MOVE, startDate, endDate);
                    break;

                case 4:
                    System.exit(0);
                    break;
            }
        }

    }


}
