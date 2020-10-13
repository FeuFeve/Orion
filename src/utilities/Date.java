package utilities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class Date {

    static int year, month, day, hour, minute, second, millis;


    private static void update() {
        LocalDateTime now = LocalDateTime.now();
        year = now.getYear();
        month = now.getMonthValue();
        day = now.getDayOfMonth();
        hour = now.getHour();
        minute = now.getMinute();
        second = now.getSecond();
        millis = now.get(ChronoField.MILLI_OF_SECOND);
    }

    public static String getRealDate() {
        update();
        return String.format("%d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }

    public static String getRealDateMs() {
        update();
        return String.format("%d-%02d-%02d %02d:%02d:%02d.%03d", year, month, day, hour, minute, second, millis);
    }

    public static String getRealTime() {
        update();
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public static String getRealTimeMs() {
        update();
        return String.format("%02d:%02d:%02d.%03d", hour, minute, second, millis);
    }
}
