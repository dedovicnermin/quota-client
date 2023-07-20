package io.nermdev.kafka.quota_client;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateFormatter {
    static final String format = "yyyy-MM-dd HH:mm:ss";
    public static String formatDateToString(Date date,
                                            String timeZone) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        return Optional.ofNullable(date)
                .map(d -> {
                    sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
                    return sdf.format(d);
                }).orElseGet(() -> {
                    sdf.setTimeZone(TimeZone.getTimeZone(Calendar.getInstance().getTimeZone().getID()));
                    return sdf.format(new Date(System.currentTimeMillis()));
                });
    }
}
