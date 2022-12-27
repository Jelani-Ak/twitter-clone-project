package com.jelaniak.twittercloneproject.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Helper {
    public Helper() {
    }

    public static String getTimeNow() {
        String timeNow = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ";
        return timeNow;
    }
}
