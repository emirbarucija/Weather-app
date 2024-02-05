package com.example.weatherapp.util;

import lombok.experimental.UtilityClass;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public class DateUtil {
    private static final String PATTERN = "yyyyMMdd";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(PATTERN);

    public static String getDay(Long dateLong) {
        Date date = SIMPLE_DATE_FORMAT.parse(String.valueOf(dateLong), new ParsePosition(0));

        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
