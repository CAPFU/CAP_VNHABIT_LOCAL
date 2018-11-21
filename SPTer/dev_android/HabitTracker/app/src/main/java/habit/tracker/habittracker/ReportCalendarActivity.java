package habit.tracker.habittracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import habit.tracker.habittracker.adapter.calendar.TrackingCalendarAdapter;
import habit.tracker.habittracker.adapter.calendar.TrackingCalendarItem;
import habit.tracker.habittracker.api.VnHabitApiUtils;
import habit.tracker.habittracker.api.model.tracking.Tracking;
import habit.tracker.habittracker.api.model.tracking.TrackingList;
import habit.tracker.habittracker.api.service.VnHabitApiService;
import habit.tracker.habittracker.common.util.AppGenerator;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.habit.HabitEntity;
import habit.tracker.habittracker.repository.habit.HabitTracking;
import habit.tracker.habittracker.repository.tracking.TrackingEntity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static habit.tracker.habittracker.common.AppConstant.DEBUG_TAG;

public class ReportCalendarActivity extends AppCompatActivity implements TrackingCalendarAdapter.OnItemClickListener {

    @BindView(R.id.header)
    View vHeader;

    @BindView(R.id.tvHabitName)
    TextView tvHabitName;
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
    @BindView(R.id.tvCurrentChain)
    TextView tvCurrentChain;
    @BindView(R.id.tvBestTrackingChain)
    TextView tvBestTrackingChain;

    @BindView(R.id.tvCalendarHead)
    TextView tvCalendarHead;

    @BindView(R.id.tabEditHabit)
    View tabEditHabit;
    @BindView(R.id.tabChart)
    View tabChart;

    private HabitEntity habitEntity;
    TrackingCalendarAdapter calendarAdapter;
    List<TrackingCalendarItem> calendarItemList = new ArrayList<>();

    int timeLine = 0;
    String defaultCurrentDate;
    String currentDate;
    String lastDayPreMonth;
    String firstDayNextMonth;
    int curTrackingCount;
    int totalCount = 0;
    boolean[] availDaysInWeek = new boolean[7];

    List<TrackingEntity> curTrackingChain = new ArrayList<>();
    List<TrackingEntity> bestTrackingChain = new ArrayList<>();

    Database appDatabase = Database.getInstance(this);
    boolean isDbOpen = false;

    float touchX = 0;
    float touchY = 0;
    float boundTop = 0;
    float boundBottom = 0;
    float touchThreshTime = 90;
    float touchTimeThresh = 100;
    long lastTouchTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report_calendar);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String habitId = bundle.getString(MainActivity.HABIT_ID);
            initializeScreen(habitId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == HabitActivity.REQUEST_UPDATE) {
            boolean delete = false;
            if (data != null) {
                delete = data.getBooleanExtra("delete", false);
            }
            if (!delete) {
                initializeScreen(habitEntity.getHabitId());

            } else {
                finish();
            }
        }
    }

    private void initializeScreen(String habitId) {
        appDatabase.open();
        isDbOpen = true;

        currentDate = AppGenerator.getCurrentDate(AppGenerator.YMD_SHORT);
        defaultCurrentDate = currentDate;
        habitEntity = Database.getHabitDb().getHabit(habitId);
        String habitColor = habitEntity.getHabitColor();
        TrackingEntity trackingEntity = Database.getTrackingDb().getTracking(habitId, currentDate);
        List<TrackingEntity> totalList = Database.getTrackingDb().getRecordByHabit(habitId);

        if (trackingEntity != null) {
            curTrackingCount = Integer.parseInt(trackingEntity.getCount());
        }
        vHeader.setBackgroundColor(ColorUtils.setAlphaComponent(Color.parseColor(habitColor), 100));

        availDaysInWeek[0] = habitEntity.getMon().equals("1");
        availDaysInWeek[1] = habitEntity.getTue().equals("1");
        availDaysInWeek[2] = habitEntity.getWed().equals("1");
        availDaysInWeek[3] = habitEntity.getThu().equals("1");
        availDaysInWeek[4] = habitEntity.getFri().equals("1");
        availDaysInWeek[5] = habitEntity.getSat().equals("1");
        availDaysInWeek[6] = habitEntity.getSun().equals("1");

        String curDay = currentDate;
        String preDay;
        List<List<TrackingEntity>> groupList = new ArrayList<>();
        if (totalList != null && totalList.size() > 0) {
            int k = totalList.size() - 1;
            curTrackingChain.add(totalList.get(k));
            k--;
            while (k >= 0) {
                preDay = AppGenerator.getPreDate(curDay, availDaysInWeek);
                if (preDay.equals(totalList.get(k).getCurrentDate())) {
                    curTrackingChain.add(totalList.get(k));
                } else {
                    break;
                }
                curDay = preDay;
                k--;
            }

            groupList.add(new ArrayList<TrackingEntity>());
            preDay = currentDate;
            for (int i = totalList.size() - 1; i >= 0; i--) {
                if (!preDay.equals(totalList.get(i).getCurrentDate())) {
                    groupList.add(new ArrayList<TrackingEntity>());
                }
                groupList.get(groupList.size() - 1).add(totalList.get(i));
                preDay = AppGenerator.getPreDate(totalList.get(i).getCurrentDate(), availDaysInWeek);
            }
            int l = 0;
            for (List<TrackingEntity> chain : groupList) {
                if (chain.size() > l) {
                    bestTrackingChain = chain;
                    l = chain.size();
                }
            }
        }

        calendarAdapter = new TrackingCalendarAdapter(this, calendarItemList, habitEntity.getHabitColor());
        calendarAdapter.setClickListener(this);
        recyclerViewCalendar.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerViewCalendar.setAdapter(calendarAdapter);
        loadCalendar(currentDate);

        updateUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isDbOpen) {
            appDatabase.open();
        }
    }

    private void loadCalendar(String currentDate) {
        calendarItemList.clear();
        List<TrackingCalendarItem> head = new ArrayList<>();
        List<TrackingCalendarItem> tail = new ArrayList<>();

        String[] datesInMonth = AppGenerator.getDatesInMonth(currentDate, false);
        Calendar calendar = Calendar.getInstance();
        firstDayNextMonth = AppGenerator.getNextDate(datesInMonth[datesInMonth.length - 1], AppGenerator.YMD_SHORT);
        lastDayPreMonth = AppGenerator.getPreDate(datesInMonth[0], AppGenerator.YMD_SHORT);

        try {
            String cur = datesInMonth[0];
            String next;
            String pre;

            SimpleDateFormat format = new SimpleDateFormat(AppGenerator.YMD_SHORT, Locale.getDefault());
            Date d = format.parse(datesInMonth[0]);
            calendar.setTimeInMillis(d.getTime());

            int dayInW = calendar.get(Calendar.DAY_OF_WEEK);
            int padding = getPadding(dayInW);
            for (int i = 0; i < padding; i++) {
                pre = AppGenerator.getPreDate(cur, AppGenerator.YMD_SHORT);
                head.add(new TrackingCalendarItem(pre.split("-")[2], pre, false, true));
                cur = pre;
            }
            Collections.reverse(head);

            d = format.parse(datesInMonth[datesInMonth.length - 1]);
            calendar.setTimeInMillis(d.getTime());
            dayInW = calendar.get(Calendar.DAY_OF_WEEK);
            padding = 6 - getPadding(dayInW);
            cur = datesInMonth[datesInMonth.length - 1];
            for (int i = 0; i < padding; i++) {
                next = AppGenerator.getNextDate(cur, AppGenerator.YMD_SHORT);
                tail.add(new TrackingCalendarItem(next.split("-")[2], next, false, true));
                cur = next;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvCalendarHead.setText("Tháng " + (calendar.get(Calendar.MONTH) + 1) + "/ " + calendar.get(Calendar.YEAR));

        calendarItemList.add(new TrackingCalendarItem("Hai", null, false, false, true));
        calendarItemList.add(new TrackingCalendarItem("Ba", null, false, false, true));
        calendarItemList.add(new TrackingCalendarItem("Tư", null, false, false, true));
        calendarItemList.add(new TrackingCalendarItem("Năm", null, false, false, true));
        calendarItemList.add(new TrackingCalendarItem("Sáu", null, false, false, true));
        calendarItemList.add(new TrackingCalendarItem("Bảy", null, false, false, true));
        calendarItemList.add(new TrackingCalendarItem("CN", null, false, false, true));

        // add imgPreDate month item
        calendarItemList.addAll(head);

        Map<String, TrackingEntity> mapValues = loadData(currentDate);
        boolean[] watchDay = new boolean[7];
        watchDay[0] = habitEntity.getMon().equals("1");
        watchDay[1] = habitEntity.getTue().equals("1");
        watchDay[2] = habitEntity.getWed().equals("1");
        watchDay[3] = habitEntity.getThu().equals("1");
        watchDay[4] = habitEntity.getFri().equals("1");
        watchDay[5] = habitEntity.getSat().equals("1");
        watchDay[6] = habitEntity.getSun().equals("1");

        for (int i = 0; i < datesInMonth.length; i++) {
            if (mapValues.containsKey(datesInMonth[i])) {
                calendarItemList.add(new TrackingCalendarItem(String.valueOf(i + 1), datesInMonth[i], true, false));
            } else {
                calendarItemList.add(new TrackingCalendarItem(String.valueOf(i + 1), datesInMonth[i], false, false));
            }
        }

        // add imgNextDate month item
        calendarItemList.addAll(tail);
        calendarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        int[] outLocation = new int[2];
        recyclerViewCalendar.getLocationOnScreen(outLocation);
        boundTop = outLocation[1];
        boundBottom = outLocation[1] + recyclerViewCalendar.getHeight();
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                touchX = ev.getX();
                touchY = ev.getY();
                Log.d(DEBUG_TAG, "DOWN");
                break;
            case (MotionEvent.ACTION_MOVE):
                if (ev.getY() > boundTop && ev.getY() < boundBottom) {
                    if (System.currentTimeMillis() - lastTouchTime > touchTimeThresh) {
                        if (ev.getX() - touchX > touchThreshTime && Math.abs(ev.getY() - touchY) < touchThreshTime) {

                            String pre = AppGenerator.getFirstDatePreMonth(currentDate, AppGenerator.YMD_SHORT, AppGenerator.YMD_SHORT);
                            timeLine = AppGenerator.countDayBetween(pre, currentDate) * -1;
                            currentDate = pre;
                            loadCalendarByDate(currentDate);
                            updateUI();
                            Log.d(DEBUG_TAG, "Action was MOVE: right");
                        } else if (touchX - ev.getX() > touchThreshTime && Math.abs(ev.getY() - touchY) < touchThreshTime) {

                            String next = AppGenerator.getFirstDateNextMonth(currentDate, AppGenerator.YMD_SHORT, AppGenerator.YMD_SHORT);
                            timeLine = AppGenerator.countDayBetween(currentDate, next);
                            currentDate = next;
                            loadCalendarByDate(currentDate);
                            updateUI();
                            Log.d(DEBUG_TAG, "Action was MOVE: left");
                        }
                        lastTouchTime = System.currentTimeMillis();
                    }
                }
                Log.d(DEBUG_TAG, "MOVE");
                break;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "UP");
                break;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "CANCEL");
                break;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "OUTSIDE bounds of current screen element");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onItemClick(View v, int position) {
        TrackingCalendarItem item = calendarItemList.get(position);
        currentDate = item.getDate();

        timeLine = AppGenerator.countDayBetween(item.getDate(), defaultCurrentDate);
        timeLine = currentDate.compareTo(firstDayNextMonth) < 0? timeLine * -1: timeLine;

        TrackingEntity trackingEntity = Database.getTrackingDb().getTracking(habitEntity.getHabitId(), currentDate);
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
                currentDate = AppGenerator.getPreDate(currentDate, AppGenerator.YMD_SHORT);
                break;
            case R.id.next:
                timeLine++;
                currentDate = AppGenerator.getNextDate(currentDate, AppGenerator.YMD_SHORT);
                break;
        }
        loadCalendarByDate(currentDate);
        updateUI();
    }

    private void loadCalendarByDate(String currentDate) {
        if (timeLine <= 0) {
            // get today tracking record of current habit
            TrackingEntity todayTracking = Database.trackingImpl.getTracking(habitEntity.getHabitId(), currentDate);
            curTrackingCount = 0;
            if (todayTracking != null) {
                curTrackingCount = Integer.parseInt(todayTracking.getCount());
            }
        }
    }

    @OnClick({R.id.minusCount, R.id.addCount})
    public void onTrackingCountChanged(View v) {
        if (timeLine > 0
                || currentDate.compareTo(habitEntity.getStartDate()) < 0
                || !AppGenerator.isValidTrackingDay(currentDate, availDaysInWeek)) {
            return;
        }

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

        // save to appDatabase
        TrackingEntity record =
                Database.trackingImpl.getTracking(this.habitEntity.getHabitId(), this.currentDate);
        if (record == null) {
            record = new TrackingEntity();
            record.setTrackingId(AppGenerator.getNewId());
            record.setHabitId(this.habitEntity.getHabitId());
            record.setCurrentDate(this.currentDate);
        }
        record.setCount(String.valueOf(curTrackingCount));
        Database.trackingImpl.saveTracking(record);

        updateUI();

        TrackingList trackingData = new TrackingList();
        Tracking tracking = new Tracking();
        tracking.setTrackingId(record.getTrackingId());
        tracking.setHabitId(record.getHabitId());
        tracking.setCurrentDate(currentDate);
        tracking.setCount(String.valueOf(record.getCount()));
        trackingData.getTrackingList().add(tracking);
        VnHabitApiService service = VnHabitApiUtils.getApiService();
        service.updateTracking(trackingData).enqueue(new Callback<ResponseBody>() {
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
        } else if (timeLine == 1) {
            tvCurrentTime.setText("Ngày mai");
        } else if (timeLine == -1) {
            tvCurrentTime.setText("Hôm qua");
        } else {
            tvCurrentTime.setText(
                    AppGenerator.format(currentDate, AppGenerator.YMD_SHORT, AppGenerator.DMY_SHORT));
        }

        if (timeLine <= 0
                && currentDate.compareTo(habitEntity.getStartDate()) >= 0
                && AppGenerator.isValidTrackingDay(currentDate, availDaysInWeek)) {
            tvTrackCount.setText(String.valueOf(curTrackingCount));
        } else {
            tvTrackCount.setText("--");
        }

        tvHabitName.setText(habitEntity.getHabitName());

        tvTotalCount.setText(String.valueOf(totalCount));

        tvCurrentChain.setText(String.valueOf(curTrackingChain.size()));

        tvBestTrackingChain.setText(String.valueOf(bestTrackingChain.size()));

        // reload calendar
        if ((currentDate.compareTo(lastDayPreMonth) <= 0
                || currentDate.compareTo(firstDayNextMonth) >= 0)) {
            loadCalendar(currentDate);
        }
    }

    public Map<String, TrackingEntity> loadData(String currentDate) {
        String[] daysInMonth = AppGenerator.getDatesInMonth(currentDate, false);
        String startReportDate = daysInMonth[0];

        Map<String, TrackingEntity> mapDayInMonth = new HashMap<>(31);
        HabitTracking habitTracking = Database.getTrackingDb().getHabitTrackingBetween(habitEntity.getHabitId(), startReportDate, defaultCurrentDate);
        if (habitTracking != null) {
            totalCount = habitTracking.getTrackingList().size();
            for (TrackingEntity entity : habitTracking.getTrackingList()) {
                mapDayInMonth.put(entity.getCurrentDate(), entity);
            }
            habitEntity = habitTracking.getHabit();
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

    @OnClick(R.id.tabEditHabit)
    public void editHabitDetails(View v) {
        Intent intent = new Intent(this, HabitActivity.class);
        intent.putExtra(MainActivity.HABIT_ID, this.habitEntity.getHabitId());
        startActivityForResult(intent, HabitActivity.REQUEST_UPDATE);
    }

    @OnClick(R.id.tabChart)
    public void showDetailsChart(View v) {
        Intent intent = new Intent(this, ReportDetailsActivity.class);
        intent.putExtra(MainActivity.HABIT_ID, habitEntity.getHabitId());
        intent.putExtra(MainActivity.HABIT_COLOR, habitEntity.getHabitColor());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        appDatabase.close();
        isDbOpen = false;
        super.onStop();
    }
}
