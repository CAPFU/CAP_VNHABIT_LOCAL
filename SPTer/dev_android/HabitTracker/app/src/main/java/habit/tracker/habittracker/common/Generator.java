package habit.tracker.habittracker.common;

import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static String getPreMonth(String currentDate) {
        String[] arrDate = currentDate.split("-");
        int month = Integer.parseInt(arrDate[1]) - 1;
        if (month < 1 || month > 12) {
            return currentDate;
        }
        return formatDate(arrDate[0] + "-" + month + "-" + arrDate[2]);
    }

    public static String getNextMonth(String currentDate) {
        String[] arrDate = currentDate.split("-");
        int month = Integer.parseInt(arrDate[1]) + 1;
        if (month < 1 || month > 12) {
            return currentDate;
        }
        return formatDate(arrDate[0] + "-" + month + "-" + arrDate[2]);
    }

    public static String getPreYear(String currentDate) {
        String[] arrDate = currentDate.split("-");
        int year = Integer.parseInt(arrDate[0]) - 1;
        if (year < 1970) {
            return currentDate;
        }
        return formatDate(year + "-" + arrDate[1] + "-" + arrDate[2]);
    }

    public static String getNextYear(String currentDate) {
        String[] arrDate = currentDate.split("-");
        int year = Integer.parseInt(arrDate[0]) + 1;
        if (year > 2020) {
            return currentDate;
        }
        return formatDate(year + "-" + arrDate[1] + "-" + arrDate[2]);
    }

    private static String formatDate(String currentDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return dateFormat.format(dateFormat.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param month start from 0
     */
    public static int getMaxDayInMonth(int year, int month){
        Calendar ca = Calendar.getInstance();
        ca.set(year, month, 1);
        return ca.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String[] getDatesInWeek(String currentDate) {

        String[] arrDate = currentDate.split("-");
        int year = Integer.parseInt(arrDate[0]);
        int month = Integer.parseInt(arrDate[1]);
        int date = Integer.parseInt(arrDate[2]);

        Calendar ca = Calendar.getInstance();
        ca.set(year, month - 1, date);
        date = ca.get(Calendar.DAY_OF_WEEK);

        int index = 0;
        String[] week = new String[7];
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
     *
     * @param year
     * @param month start from 0
     * @param date
     * @return
     */
    public static String[] getDatesInMonth(int year, int month, int date) {
        String currentDate = year + "-" + month + "-" + date;
        Calendar ca = Calendar.getInstance();
        ca.set(year, month - 1, date);
        int numberOfDays = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] daysInMonth = new String[numberOfDays];
        daysInMonth[date - 1] = currentDate;
        for (int i = date - 2; i >= 0; i--) {
            daysInMonth[i] = getPreDate(currentDate);
            currentDate = daysInMonth[i];
        }
        currentDate = daysInMonth[date - 1];
        for (int i = date; i < numberOfDays; i++) {
            daysInMonth[i] = getNextDate(currentDate);
            currentDate = daysInMonth[i];
        }
        return daysInMonth;
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

    public static String convert(String date, String p1, String p2) {
        String[] strs = date.split(p1);
        return strs[2] + p2 + strs[1] + p2 + strs[0];
    }
}