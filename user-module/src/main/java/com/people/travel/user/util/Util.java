package com.people.travel.user.util;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

@Log4j2
public final class Util {
    public static String evoCalendarEventFormat(LocalDate date) {

        if(date==null){
            throw new IllegalStateException("Empty date");
        }

        String month = date.getMonth().name();
        int day = date.getDayOfMonth();
        int year = date.getYear();
        return month + "/" + day + "/" + year;
    }

    public static String evoCalendarBadgeDurationFormat(LocalDate startDate, LocalDate endDate){
        if(startDate==null || endDate==null){
            throw new IllegalStateException("Empty startDate or endDate");
        }
        return evoCalendarBadgeFormat(startDate)+" - "+ evoCalendarBadgeFormat(endDate);
    }

    private static String evoCalendarBadgeFormat(LocalDate date){
        if(date==null){
            throw new IllegalStateException("Empty date");
        }
        int month = date.getMonth().getValue();
        int day = date.getDayOfMonth();

        String monthStr = month > 10 ? String.valueOf(month) : "0" + month;
        String dayStr = day > 10 ? String.valueOf(day) : "0" + day;
        return monthStr + "/" + dayStr;
    }
}
