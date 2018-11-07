package habit.tracker.habittracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import habit.tracker.habittracker.adapter.CalendarItem;
import habit.tracker.habittracker.adapter.TrackingCalendarAdapter;
import habit.tracker.habittracker.common.util.AppGenerator;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.habit.HabitEntity;
import habit.tracker.habittracker.repository.tracking.HabitTracking;
import habit.tracker.habittracker.repository.tracking.TrackingEntity;

public class ReportSummaryActivity extends AppCompatActivity implements TrackingCalendarAdapter.OnItemClickListener {

    @BindView(R.id.header)
    View vHeader;

    @BindView(R.id.tvCurrentTime)
    TextView tvCurrentTime;
    @BindView(R.id.tvTrackCount)
    TextView tvTrackCount;

    @BindView(R.id.calendar)
    RecyclerView recyclerViewCalendar;

    private HabitEntity habitEntity;
    TrackingCalendarAdapter calendarAdapter;
    List<CalendarItem> calendarItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report_summary);
        ButterKnife.bind(this);

        // UI
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String habitId = bundle.getString(MainActivity.HABIT_ID);
            String habitColor = bundle.getString(MainActivity.HABIT_COLOR);

            Database db = Database.getInstance(this);
            db.open();
            this.habitEntity = Database.sHabitDaoImpl.getHabit(habitId);
            db.close();

            vHeader.setBackgroundColor(ColorUtils.setAlphaComponent(Color.parseColor(habitColor), 100));
        }

        // data
        List<CalendarItem> head = new ArrayList<>();
        List<CalendarItem> tail = new ArrayList<>();

        String currentDate = AppGenerator.getCurrentDate(AppGenerator.formatYMD2);
        String[] datesInMonth = AppGenerator.getDatesInMonth(currentDate, false);
        Calendar calendar = Calendar.getInstance();

        try {
            SimpleDateFormat format = new SimpleDateFormat(AppGenerator.formatYMD2, Locale.getDefault());
            Date d = format.parse(datesInMonth[0]);
            calendar.setTimeInMillis(d.getTime());

            int dayInW = calendar.get(Calendar.DAY_OF_WEEK);

            int padding = getPadding(dayInW);
            for (int i = 0; i < padding; i++) {
                head.add(new CalendarItem(null, false, false));
            }

            d = format.parse(datesInMonth[datesInMonth.length - 1]);
            calendar.setTimeInMillis(d.getTime());
            dayInW = calendar.get(Calendar.DAY_OF_WEEK);

            padding = getPadding(dayInW);
            for (int i = 0; i < padding; i++) {
                tail.add(new CalendarItem(null, false, false));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendarItemList.add(new CalendarItem("Hai", false, false));
        calendarItemList.add(new CalendarItem("Ba", false, false));
        calendarItemList.add(new CalendarItem("Tư", false, false));
        calendarItemList.add(new CalendarItem("Năm", false, false));
        calendarItemList.add(new CalendarItem("Sáu", false, false));
        calendarItemList.add(new CalendarItem("Bảy", false, false));
        calendarItemList.add(new CalendarItem("CN", false, false));

        // add pre month item
        calendarItemList.addAll(head);

        Map<String, TrackingEntity> mapValues = loadMonthData(currentDate);
        boolean[] watchDay = new boolean[7];
        watchDay[0] = habitEntity.getMon().equals("1");
        watchDay[1] = habitEntity.getTue().equals("1");
        watchDay[2] = habitEntity.getWed().equals("1");
        watchDay[3] = habitEntity.getThu().equals("1");
        watchDay[4] = habitEntity.getFri().equals("1");
        watchDay[5] = habitEntity.getSat().equals("1");
        watchDay[6] = habitEntity.getSun().equals("1");

        // add days in month
        boolean isClickable;
        int pos;
        for (int i = 0; i < datesInMonth.length; i++) {

            pos = (head.size() + i + 1) % 7 - 1;

            if (pos >= 0) {
                isClickable = watchDay[pos];
            } else {
                isClickable = watchDay[6];
            }

            if (mapValues.containsKey(datesInMonth[i])) {
                calendarItemList.add(new CalendarItem(String.valueOf(i + 1), true, isClickable));
            } else {
                calendarItemList.add(new CalendarItem(String.valueOf(i + 1), false, isClickable));
            }
        }

        // add next month item
        calendarItemList.addAll(tail);

        calendarAdapter = new TrackingCalendarAdapter(this, calendarItemList);
        calendarAdapter.setClickListener(this);
        recyclerViewCalendar.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerViewCalendar.setAdapter(calendarAdapter);
    }

    @Override
    public void onItemClick(View v, int position) {

    }

    private boolean checkClickable(int position) {

        return false;
    }

    public Map<String, TrackingEntity> loadMonthData(String currentDate) {
        String[] daysInMonth = AppGenerator.getDatesInMonth(currentDate, false);
        String startReportDate = daysInMonth[0];
        String endReportDate = daysInMonth[daysInMonth.length - 1];

        Map<String, TrackingEntity> mapDayInMonth = new HashMap<>(31);

        Database db = Database.getInstance(this);
        db.open();
        HabitTracking habitTracking = Database.sTrackingImpl
                .getHabitTrackingBetween(this.habitEntity.getHabitId(), startReportDate, endReportDate);
        db.close();

        for (TrackingEntity entity: habitTracking.getTrackingEntityList()) {
            mapDayInMonth.put(entity.getCurrentDate(), entity);
        }

        if (habitTracking.getHabitEntity() != null) {
            this.habitEntity = habitTracking.getHabitEntity();
        }

        if (mapDayInMonth.size() == 0) {
            return null;
        }
        return mapDayInMonth;
    }

    private int getPadding(int dayInW) {
        int padding = 0;
        switch (dayInW) {
            case Calendar.MONDAY:
                break;
            case Calendar.TUESDAY:
                padding = 1;
                break;
            case Calendar.WEDNESDAY:
                padding = 2;
                break;
            case Calendar.THURSDAY:
                padding = 3;
                break;
            case Calendar.FRIDAY:
                padding = 4;
                break;
            case Calendar.SATURDAY:
                padding = 5;
                break;
            case Calendar.SUNDAY:
                padding = 6;
                break;
        }
        return padding;
    }

    public void showEmpty(View view) {
        Intent intent = new Intent(this, EmptyActivity.class);
        startActivity(intent);
    }
}
