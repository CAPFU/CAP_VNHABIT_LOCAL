package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import habit.tracker.habittracker.common.chart.ChartHelper;
import habit.tracker.habittracker.common.util.AppGenerator;
import habit.tracker.habittracker.common.util.MySharedPreference;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.habit.DateTracking;
import habit.tracker.habittracker.repository.habit.HabitEntity;
import habit.tracker.habittracker.repository.tracking.TrackingEntity;


public class ReportActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    @BindView(R.id.pre)
    View pre;
    @BindView(R.id.next)
    View next;
    @BindView(R.id.displayTime)
    TextView time;
    @BindView(R.id.total)
    TextView tvTotal;
    @BindView(R.id.totalDone)
    TextView tvTotalDone;
    @BindView(R.id.tabWeek)
    View tabWeek;
    @BindView(R.id.tabMonth)
    View tabMonth;
    @BindView(R.id.tabYear)
    View tabYear;
    View selectedTab = tabWeek;

    @BindView(R.id.chart)
    BarChart chart;
    ChartHelper chartHelper;

    public static final int MODE_WEEK = 0;
    public static final int MODE_MONTH = 1;
    public static final int MODE_YEAR = 2;
    private int mode = MODE_WEEK;
    String currentDate;
    String firstCurrentDate;

    public int getMode() {
        return mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        int startColor = ContextCompat.getColor(this, R.color.red1);
        int endColor = ContextCompat.getColor(this, R.color.red2);
        switch (mode) {
            case MODE_WEEK:
                startColor = ContextCompat.getColor(this, R.color.red1);
                endColor = ContextCompat.getColor(this, R.color.red2);
                break;
            case MODE_MONTH:
                startColor = ContextCompat.getColor(this, R.color.purple1);
                endColor = ContextCompat.getColor(this, R.color.purple2);
                break;
            case MODE_YEAR:
                startColor = ContextCompat.getColor(this, R.color.blue1);
                endColor = ContextCompat.getColor(this, R.color.blue2);
                break;
            default:
                break;
        }
        chartHelper = new ChartHelper(this, chart);
        chartHelper.initChart();
        chartHelper.setChartColor(startColor, endColor);

        initializeScreen();
    }

    private void initializeScreen() {
        try {
            currentDate = AppGenerator.getCurrentDate(AppGenerator.YMD_SHORT);

            Database db = Database.getInstance(this);
            db.open();
            String userId = MySharedPreference.getUserId(this);
            int countHabit = Database.getHabitDb().countHabitByUser(userId);
            int countTracking = Database.getTrackingDb().countTrackByUser(userId);
            db.close();
            tvTotal.setText(String.valueOf(countHabit));
            tvTotalDone.setText(String.valueOf(countTracking));

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            currentDate = dateFormat.format(dateFormat.parse(currentDate));
            firstCurrentDate = currentDate;

            ArrayList<BarEntry> values = loadWeekData(currentDate);
            chartHelper.setData(values, mode);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tabWeek, R.id.tabMonth, R.id.tabYear})
    public void loadReportByMode(View v) {
        unselect(selectedTab);
        switch (v.getId()) {
            case R.id.tabWeek:
                mode = MODE_WEEK;
                select(tabWeek);
                selectedTab = tabWeek;
                break;
            case R.id.tabMonth:
                mode = MODE_MONTH;
                select(tabMonth);
                selectedTab = tabMonth;
                break;
            case R.id.tabYear:
                mode = MODE_YEAR;
                select(tabYear);
                selectedTab = tabYear;
                break;
        }
        ArrayList<BarEntry> values = loadData(currentDate);
        if (values != null && values.size() > 0) {
            chartHelper.setData(values, mode);
        }
    }

    @OnClick(R.id.pre)
    public void pre(View v) {
        switch (mode) {
            case MODE_WEEK:
                currentDate = AppGenerator.getDayPreWeek(currentDate);
                break;
            case MODE_MONTH:
                currentDate = AppGenerator.getPreMonth(currentDate);
                break;
            case MODE_YEAR:
                currentDate = AppGenerator.getPreYear(currentDate);
                break;
        }
        ArrayList<BarEntry> values = loadData(currentDate);
        chartHelper.setData(values, mode);
    }

    @OnClick(R.id.next)
    public void next(View v) {
        switch (mode) {
            case MODE_WEEK:
                currentDate = AppGenerator.getDayNextWeek(currentDate);
                break;
            case MODE_MONTH:
                currentDate = AppGenerator.getNextMonth(currentDate);
                break;
            case MODE_YEAR:
                currentDate = AppGenerator.getNextYear(currentDate);
                break;
        }
        ArrayList<BarEntry> values = loadData(currentDate);
        chartHelper.setData(values, mode);
    }

    private ArrayList<BarEntry> loadData(String currentTime) {
        ArrayList<BarEntry> values = null;
        switch (mode) {
            case MODE_WEEK:
                values = loadWeekData(currentTime);
                break;
            case MODE_MONTH:
                values = loadMonthData(currentTime);
                break;
            case MODE_YEAR:
                values = loadYearData(currentTime);
                break;
            default:
                break;
        }
        return values;
    }

    private ArrayList<BarEntry> loadWeekData(String currentDate) {
        ArrayList<BarEntry> values = new ArrayList<>();
        String[] daysInWeek = AppGenerator.getDatesInWeek(currentDate);
        String startDate = AppGenerator.format(daysInWeek[0], AppGenerator.YMD_SHORT, AppGenerator.DMY_SHORT);
        String endDate = AppGenerator.format(daysInWeek[6], AppGenerator.YMD_SHORT, AppGenerator.DMY_SHORT);
        time.setText(startDate + " - " + endDate);

        Database db = new Database(this);
        db.open();

        // get all completed habits in one week
        List<DateTracking> weekData = Database
                .habitDaoImpl.getHabitsBetween(daysInWeek[0], daysInWeek[6]);
        db.close();

        Map<String, Integer> countHabit = new HashMap<>();
        List<DateTracking> completedList = new ArrayList<>();
        HabitEntity hb;
        TrackingEntity tr;
        for (DateTracking item : weekData) {
            hb = item.getHabitEntity();
            tr = item.getTrackingEntity();

            if (hb.getMonitorNumber() != null

                    && tr.getCount() != null

                    && tr.getCount().compareTo(hb.getMonitorNumber()) >= 0 )
            {
                completedList.add(item);
            }

            countHabit.put(hb.getHabitId(), 0);
        }

        int[] count = new int[7];
        for (int i = 0; i < completedList.size(); i++) {
            String diw = completedList.get(i)
                    .getTrackingEntity().getCurrentDate();

            if (diw.equals(daysInWeek[0])) {
                ++count[0];
            } else if (diw.equals(daysInWeek[1])) {
                ++count[1];
            } else if (diw.equals(daysInWeek[2])) {
                ++count[2];
            } else if (diw.equals(daysInWeek[3])) {
                ++count[3];
            } else if (diw.equals(daysInWeek[4])) {
                ++count[4];
            } else if (diw.equals(daysInWeek[5])) {
                ++count[5];
            } else if (diw.equals(daysInWeek[6])) {
                ++count[6];
            }
        }
        for (int i = 1; i <= 7; i++) {
            values.add(new BarEntry(i, count[i - 1]));
        }
        return values;
    }

    private ArrayList<BarEntry> loadMonthData(String currentDate) {
        ArrayList<BarEntry> values = new ArrayList<>();
        String[] daysInMonth = AppGenerator.getDatesInMonth(currentDate, false);

        String startDate = AppGenerator.format(daysInMonth[0], AppGenerator.YMD_SHORT, AppGenerator.DMY_SHORT);
        String endDate = AppGenerator.format(daysInMonth[daysInMonth.length - 1], AppGenerator.YMD_SHORT, AppGenerator.DMY_SHORT);
        time.setText(startDate + " - " + endDate);

        Database db = new Database(this);
        db.open();

        List<DateTracking> total = Database
                .habitDaoImpl.getHabitsBetween(
                        daysInMonth[0], daysInMonth[daysInMonth.length - 1]);

        Map<String, Integer> countHabit = new HashMap<>();
        List<DateTracking> completedList = new ArrayList<>();
        HabitEntity hb;
        TrackingEntity tr;
        for (DateTracking item : total) {
            hb = item.getHabitEntity();
            tr = item.getTrackingEntity();

            if (hb.getMonitorNumber() != null

                    && tr.getCount() != null

                    && tr.getCount().compareTo(hb.getMonitorNumber()) >= 0 )
            {
                completedList.add(item);
            }
            countHabit.put(hb.getHabitId(), 0);
        }

        int[] count = new int[daysInMonth.length];
        for (DateTracking item : completedList) {
            tr = item.getTrackingEntity();
            for (int i = 0; i < daysInMonth.length; i++) {
                if (tr.getCurrentDate().equals(daysInMonth[i])) {
                    ++count[i];
                }
            }
        }

        for (int i = 1; i <= count.length; i++) {
            values.add(new BarEntry(i, count[i - 1]));
        }
        db.close();
        return values;
    }

    private ArrayList<BarEntry> loadYearData(String currentDate) {
        ArrayList<BarEntry> values = new ArrayList<>();
        String[] arrDate = currentDate.split("-");

        int year = Integer.parseInt(arrDate[0]);
        int month = Integer.parseInt(arrDate[1]);
        int date = Integer.parseInt(arrDate[2]);

        String t = "Tháng 01" + "/" + year + " - " + "Tháng 12" + "/" + year;
        time.setText(t);

        Database db = new Database(this);
        db.open();

        List<DateTracking> monthData;

        int[] completePerMonth = new int[12];
        int completeRecord = 0;

        HabitEntity hb;
        TrackingEntity tr;

        Map<String, Integer> habitNumber = new HashMap<>();
        String start;
        String end;
        for (int m = 0; m < 12; m++) {
            start = AppGenerator.getDate(year, m + 1, 1, AppGenerator.YMD_SHORT);
            end = AppGenerator.getDate(year, m + 1, AppGenerator.getMaxDayInMonth(year, m), AppGenerator.YMD_SHORT);
            monthData = Database.getHabitDb().getHabitsBetween(start, end);

            for (DateTracking item : monthData) {
                hb = item.getHabitEntity();
                tr = item.getTrackingEntity();

                if (hb.getMonitorNumber() != null

                        && tr.getCount() != null

                        && tr.getCount().compareTo(hb.getMonitorNumber()) >= 0) {
                    completeRecord += 1;
                    ++completePerMonth[m];
                    habitNumber.put(hb.getHabitId(), 0);
                }
            }
        }

        for (int i = 1; i <= completePerMonth.length; i++) {
            values.add(new BarEntry(i, completePerMonth[i - 1]));
        }
        db.close();
        return values;
    }

    public void select(View v) {
        switch (mode) {
            case MODE_WEEK:
                v.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_tab_red));
                break;
            case MODE_MONTH:
                v.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_tab_purple));
                break;
            case MODE_YEAR:
                v.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_tab_blue));
                break;
            default:
                break;
        }
    }

    public void unselect(View v) {
        v.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void showEmpty(View view) {
        Intent intent = new Intent(this, EmptyActivity.class);
        startActivity(intent);
    }

    public void finishThis(View view) {
        finish();
    }
}
