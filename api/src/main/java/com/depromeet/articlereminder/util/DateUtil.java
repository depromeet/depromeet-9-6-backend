package com.depromeet.articlereminder.util;

import lombok.extern.log4j.Log4j2;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Log4j2
public class DateUtil {


    /**
     * String 시간을 LocalDateTime으로 변경  알림 시각 (08:30) -> 202
     *
     * @param
     * @return
     * @author topojs8
     */

    public static LocalTime getStringTOLocalDateTime(String date) throws ParseException {

        LocalTime time= LocalTime.parse(date, DateTimeFormatter.ofPattern("H:mm"));
        return time;
    }


}
