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
import butterknife.OnClick;
import habit.tracker.habittracker.adapter.CalendarItem;
import habit.tracker.habittracker.adapter.TrackingCalendarAdapter;
import habit.tracker.habittracker.api.VnHabitApiUtils;
import habit.tracker.habittracker.api.model.tracking.Tracking;
import habit.tracker.habittracker.api.model.tracking.TrackingList;
import habit.tracker.habittracker.api.service.VnHabitApiService;
import habit.tracker.habittracker.common.util.AppGenerator;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.habit.HabitEntity;
import habit.tracker.habittracker.repository.tracking.HabitTracking;
import habit.tracker.habittracker.repository.tracking.TrackingEntity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportSummaryActivity extends AppCompatActivity implements TrackingCalendarAdapter.OnItemClickListener {

    @BindView(R.id.header)
    View vHeader;

    @BindView(R.id.tvCurrentTime)
    TextView tvCurrentTime;
    @BindView(R.id.tvTrackCount)
    TextView tvTrackCount;

    @BindView(R.id.pre)
    View btnPreDate;
    @BindView(R.id.next)
    View btnNextDate;

    @BindView(R.id.minusCount)
    View imgMinusCount;
    @BindView(R.id.addCount)
    View imgAddCount;

    @BindView(R.id.calendar)
    RecyclerView recyclerViewCalendar;

    @BindView(R.id.tvTotalCount)
    TextView tvTotalCount;

    private HabitEntity habitEntity;
    TrackingCalendarAdapter calendarAdapter;
    List<CalendarItem> calendarItemList = new ArrayList<>();

    int timeLine = 0;
    String currentTrackingDate;
    int curTrackingCount;
    int curDayNumber;
    int totalCount = 0;
    List<TrackingEntity> curTrackingChain = new ArrayList<>();
    List<TrackingEntity> longestTrackingChain = new ArrayList<>();

    Database db = Database.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report_summary);
        ButterKnife.bind(this);

        List<TrackingEntity> totalList = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            db.open();

            String habitId = bundle.getString(MainActivity.HABIT_ID);
            String habitColor = bundle.getString(MainActivity.HABIT_COLOR);
            currentTrackingDate = AppGenerator.getCurrentDate(AppGenerator.formatYMD2);
            habitEntity = Database.getHabitDb().getHabit(habitId);
            TrackingEntity trackingEntity = Database.getTrackingDb().getTracking(habitId, currentTrackingDate);
            totalList = Database.getTrackingDb().getRecordByHabit(habitId);

            if (trackingEntity != null) {
                curTrackingCount = Integer.parseInt(trackingEntity.getCount());
            }
            vHeader.setBackgroundColor(ColorUtils.setAlphaComponent(Color.parseColor(habitColor), 100));
        }

        // get current tracking chain
        if (totalList != null && totalList.size() > 0) {
            int k = totalList.size() - 1;
            while (k >= 0) {

                k--;
            }
        }

        // <init calendar>
        List<CalendarItem> head = new ArrayList<>();
        List<CalendarItem> tail = new ArrayList<>();

        String[] datesInMonth = AppGenerator.getDatesInMonth(currentTrackingDate, false);
        Calendar calendar = Calendar.getInstance();

        try {
            SimpleDateFormat format = new SimpleDateFormat(AppGenerator.formatYMD2, Locale.getDefault());
            Date d = format.parse(datesInMonth[0]);
            calendar.setTimeInMillis(d.getTime());

            int dayInW = calendar.get(Calendar.DAY_OF_WEEK);

            int padding = getPadding(dayInW);
            for (int i = 0; i < padding; i++) {
                head.add(new CalendarItem(null, null, false, false));
            }

            d = format.parse(datesInMonth[datesInMonth.length - 1]);
            calendar.setTimeInMillis(d.getTime());
            dayInW = calendar.get(Calendar.DAY_OF_WEEK);

            padding = getPadding(dayInW);
            for (int i = 0; i < padding; i++) {
                tail.add(new CalendarItem(null, null, false, false));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendarItemList.add(new CalendarItem("Hai", null, false, false));
        calendarItemList.add(new CalendarItem("Ba", null, false, false));
        calendarItemList.add(new CalendarItem("Tư", null, false, false));
        calendarItemList.add(new CalendarItem("Năm", null, false, false));
        calendarItemList.add(new CalendarItem("Sáu", null, false, false));
        calendarItemList.add(new CalendarItem("Bảy", null, false, false));
        calendarItemList.add(new CalendarItem("CN", null, false, false));

        // add pre month item
        calendarItemList.addAll(head);

        Map<String, TrackingEntity> mapValues = loadData(currentTrackingDate);
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
                calendarItemList.add(new CalendarItem(String.valueOf(i + 1), datesInMonth[i], true, isClickable));
            } else {
                calendarItemList.add(new CalendarItem(String.valueOf(i + 1), datesInMonth[i], false, isClickable));
            }
            if (datesInMonth[i].equals(currentTrackingDate)) {
                curDayNumber = i + 1;
            }
        }

        // add next month item
        calendarItemList.addAll(tail);

        calendarAdapter = new TrackingCalendarAdapter(this, calendarItemList);
        calendarAdapter.setClickListener(this);
        recyclerViewCalendar.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerViewCalendar.setAdapter(calendarAdapter);
        // </init calendar>

        // init other view
        updateUI();
    }


    @Override
    public void onItemClick(View v, int position) {
        CalendarItem item = calendarItemList.get(position);
        currentTrackingDate = item.getDate();

        timeLine = Integer.parseInt(item.getText()) - curDayNumber;

        TrackingEntity trackingEntity = Database.trackingImpl.getTracking(habitEntity.getHabitId(), currentTrackingDate);

        curTrackingCount = 0;
        if (trackingEntity != null) {
            curTrackingCount = Integer.parseInt(trackingEntity.getCount());
        }

        updateUI();
    }

    @OnClick({R.id.pre, R.id.next})
    public void onDateChanged(View v) {
        switch (v.getId()) {
            case R.id.pre:
                timeLine--;
                currentTrackingDate = AppGenerator.getPreDate(currentTrackingDate, AppGenerator.formatYMD2);
                break;
            case R.id.next:
                timeLine++;
                currentTrackingDate = AppGenerator.getNextDate(currentTrackingDate, AppGenerator.formatYMD2);
                break;
        }

        if (timeLine > 0) {
            timeLine = 0;
            return;
        }

        // get today tracking record of current habit
        TrackingEntity todayTracking = Database.trackingImpl
                .getTracking(this.habitEntity.getHabitId(), this.currentTrackingDate);

        curTrackingCount = 0;
        if (todayTracking != null) {
            curTrackingCount = Integer.parseInt(todayTracking.getCount());
        }

        updateUI();
    }

    @OnClick({R.id.minusCount, R.id.addCount})
    public void onTrackingCountChanged(View v) {

        switch (v.getId()) {
            case R.id.minusCount:
                curTrackingCount = curTrackingCount - 1 < 0 ? 0 : curTrackingCount - 1;
                totalCount = totalCount - 1 < 0 ? 0 : totalCount - 1;
                break;
            case R.id.addCount:
                curTrackingCount++;
                totalCount++;
                break;
        }

        // save to db
        TrackingEntity record =
                Database.trackingImpl.getTracking(this.habitEntity.getHabitId(), this.currentTrackingDate);
        if (record == null) {
            record = new TrackingEntity();
            record.setTrackingId(AppGenerator.getNewId());
            record.setHabitId(this.habitEntity.getHabitId());
            record.setCurrentDate(this.currentTrackingDate);
        }
        record.setCount(String.valueOf(curTrackingCount));
        Database.trackingImpl.saveTracking(record);

        updateUI();

        TrackingList trackingData = new TrackingList();
        Tracking tracking = new Tracking();
        tracking.setTrackingId(record.getTrackingId());
        tracking.setHabitId(record.getHabitId());
        tracking.setCurrentDate(currentTrackingDate);
        tracking.setCount(String.valueOf(record.getCount()));
        trackingData.getTrackingList().add(tracking);
        VnHabitApiService service = VnHabitApiUtils.getApiService();
        service.replace(trackingData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void updateUI() {
        if (timeLine == 0) {
            tvCurrentTime.setText("Hôm nay");
        } else if (timeLine == -1) {
            tvCurrentTime.setText("Hôm qua");
        } else {
            tvCurrentTime.setText(
                    AppGenerator.format(currentTrackingDate, AppGenerator.formatYMD2, AppGenerator.formatDMY2));
        }

        tvTrackCount.setText(String.valueOf(curTrackingCount));

        tvTotalCount.setText(String.valueOf(totalCount));
    }

    public Map<String, TrackingEntity> loadData(String currentDate) {
        String[] daysInMonth = AppGenerator.getDatesInMonth(currentDate, false);
        String startReportDate = daysInMonth[0];
        String endReportDate = daysInMonth[daysInMonth.length - 1];

        Map<String, TrackingEntity> mapDayInMonth = new HashMap<>(31);

        HabitTracking habitTracking = Database.trackingImpl
                .getHabitTrackingBetween(this.habitEntity.getHabitId(), startReportDate, endReportDate);

        for (TrackingEntity entity : habitTracking.getTrackingEntityList()) {
            mapDayInMonth.put(entity.getCurrentDate(), entity);
            totalCount += entity.getIntCount();
        }

        if (habitTracking.getHabitEntity() != null) {
            habitEntity = habitTracking.getHabitEntity();
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

    @Override
    protected void onStop() {
        db.close();
        super.onStop();
    }
}
