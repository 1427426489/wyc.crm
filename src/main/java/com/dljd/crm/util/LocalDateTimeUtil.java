package com.dljd.crm.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String localToStr(LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern(PATTERN).format(localDateTime);
    }

    public static LocalDateTime strToLocal(String time){
        return (LocalDateTime) DateTimeFormatter.ofPattern(PATTERN).parse(time);
    }

}
