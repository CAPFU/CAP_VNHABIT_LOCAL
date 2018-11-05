package habit.tracker.habittracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import habit.tracker.habittracker.common.chart.ChartHelper;
import habit.tracker.habittracker.common.util.AppGenerator;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.habit.DateTracking;
import habit.tracker.habittracker.repository.habit.HabitEntity;
import habit.tracker.habittracker.repository.tracking.HabitTracking;
import habit.tracker.habittracker.repository.tracking.TrackingEntity;

public class ReportDetailsActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    BarChart chart;

    private String habitId;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report_details);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            habitId = bundle.getString(MainActivity.HABIT_ID);
            if (!TextUtils.isEmpty(habitId)) {
                currentDate = AppGenerator.getCurrentDate(AppGenerator.formatYMD2);
                ChartHelper chartHelper = new ChartHelper(this, chart);
                chartHelper.initChart();
                ArrayList<BarEntry> values = loadWeekData(habitId, currentDate);
                chartHelper.setData(values, ChartHelper.MODE_WEEK);
            }
        }
    }

    public ArrayList<BarEntry> loadWeekData(String habitId, String currentDate) {
        String[] daysInWeek = AppGenerator.getDatesInWeek(currentDate);

        Database db = Database.getInstance(this);
        db.open();
        HabitTracking habitTracking = Database.sTrackingImpl
                .getHabitTrackingBetween(habitId, daysInWeek[0], daysInWeek[6]);
        db.close();

        return prepareDate(habitTracking, daysInWeek);
    }

    public ArrayList<BarEntry> loadMonthData(String habitId, String currentDate) {
        String[] daysInMonth = AppGenerator.getDatesInMonth(currentDate, false);

        Database db = Database.getInstance(this);
        db.open();
        HabitTracking habitTracking = Database.sTrackingImpl
                .getHabitTrackingBetween(habitId, daysInMonth[0], daysInMonth[daysInMonth.length - 1]);
        db.close();

        return prepareDate(habitTracking, daysInMonth);
    }

    private ArrayList<BarEntry> loadYearData(String habitId, String currentDate) {
        ArrayList<BarEntry> values = new ArrayList<>();
        String[] arrDate = currentDate.split("-");

        int year = Integer.parseInt(arrDate[0]);

        Database db = new Database(this);
        db.open();

        int[] completedPerMonth = new int[12];

        HabitTracking habitTracking;
        HabitEntity hb = null;
        String start;
        String end;
        for (int m = 0; m < 12; m++) {
            start = year + "-" + (m + 1) + "-" + 1;
            end = year + "-" + (m + 1) + "-" + AppGenerator.getMaxDayInMonth(year, m);
            start = AppGenerator.format(start, AppGenerator.formatYMD2, AppGenerator.formatYMD2);
            end = AppGenerator.format(end, AppGenerator.formatYMD2, AppGenerator.formatYMD2);

            // data per month
            habitTracking = Database.sTrackingImpl
                    .getHabitTrackingBetween(habitId, start, end);

            if (habitTracking != null) {
                hb = habitTracking.getHabitEntity();
                if (hb.getMonitorNumber() != null) {
                    // data per day
                    for (TrackingEntity track : habitTracking.getTrackingEntityList()) {
                        if (track.getCount() != null
                                && track.getCount().compareTo(hb.getMonitorNumber()) >= 0) {
                            ++completedPerMonth[m];
                        }
                    }
                }
            }
        }
        db.close();

        for (int i = 1; i <= completedPerMonth.length; i++) {
            values.add(new BarEntry(i, completedPerMonth[i - 1]));
        }
        return values;
    }

    private ArrayList<BarEntry> prepareDate(HabitTracking habitTracking, String[] days) {
        ArrayList<BarEntry> values = new ArrayList<>();
        HabitEntity habit = habitTracking.getHabitEntity();
        List<TrackingEntity> trackList = habitTracking.getTrackingEntityList();

        Map<String, Integer> mapDayInMonth = new HashMap<>(31);
        for (String d : days) {
            mapDayInMonth.put(d, 0);
        }

        if (habit.getMonitorNumber() != null) {
            for (TrackingEntity track : trackList) {

                if (track.getCount() != null
                        && track.getCount().compareTo(habit.getMonitorNumber()) >= 0) {

                    mapDayInMonth.put(track.getCurrentDate(),
                            mapDayInMonth.get(track.getCurrentDate()) + 1);
                }
            }
        }

        for (int i = 1; i <= days.length; i++) {
            values.add(new BarEntry(i, mapDayInMonth.get(days[i - 1])));
        }

        return values;
    }
}
