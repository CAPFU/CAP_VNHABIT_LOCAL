package habit.tracker.habittracker.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Generator {
    public static final long MILLISECOND_IN_DAY = 86400000;
    public static final long MILLISECOND_IN_WEEK = 86400000 * 7;

    public static String getNewId() {
        String id = UUID.randomUUID().toString();
        if (id.length() > 11) {
            return id.substring(0, 11);
        }
        return id;
    }

    public static String getDayPreWeek(String currentDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = dateFormat.parse(currentDate);
            Date oneDayBefore = new Date(date.getTime() - Generator.MILLISECOND_IN_WEEK);
            return dateFormat.format(oneDayBefore);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDayNextWeek(String currentDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = dateFormat.parse(currentDate);
            Date oneDayBefore = new Date(date.getTime() + Generator.MILLISECOND_IN_WEEK);
            return dateFormat.format(oneDayBefore);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPreDate(String currentDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = dateFormat.parse(currentDate);
            Date oneDayBefore = new Date(date.getTime() - Generator.MILLISECOND_IN_DAY);
            return dateFormat.format(oneDayBefore);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNextDate(String currentDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = dateFormat.parse(currentDate);
            Date oneDayBefore = new Date(date.getTime() + Generator.MILLISECOND_IN_DAY);
            return dateFormat.format(oneDayBefore);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getWeek(int year, int month, int date) {
        String currentDate = year + "-" + month + "-" + date;
        String[] week = new String[7];
        Calendar ca = Calendar.getInstance();
        ca.set(year, month - 1, date);
        date = ca.get(Calendar.DAY_OF_WEEK);
        int index = 0;
        switch (date) {
            case Calendar.MONDAY:
                index = 0;
                break;
            case Calendar.TUESDAY:
                index = 1;
                break;
            case Calendar.WEDNESDAY:
                index = 2;
                break;
            case Calendar.THURSDAY:
                index = 3;
                break;
            case Calendar.FRIDAY:
                index = 4;
                break;
            case Calendar.SATURDAY:
                index = 5;
                break;
            case Calendar.SUNDAY:
                index = 6;
                break;
            default:
                break;
        }
        week[index] = currentDate;
        for (int i = index - 1; i >= 0; i--) {
            week[i] = getPreDate(currentDate);
            currentDate = week[i];
        }
        currentDate = week[index];
        for (int i = index + 1; i < 7; i++) {
            week[i] = getNextDate(currentDate);
            currentDate = week[i];
        }
        return week;
    }

    /**
     * The first month of the year in the Gregorian and Julian calendars is 0
     */
    public static String increase(String date) {
        String[] strs = date.split("-");
        try {
            return strs[0] + (Integer.parseInt(strs[1]) + 1) + strs[2];
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
