package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import habit.tracker.habittracker.adapter.habit.HabitRecyclerViewAdapter;
import habit.tracker.habittracker.adapter.habit.TrackingItem;
import habit.tracker.habittracker.api.VnHabitApiUtils;
import habit.tracker.habittracker.api.model.habit.Habit;
import habit.tracker.habittracker.api.model.habit.HabitResponse;
import habit.tracker.habittracker.api.model.tracking.Tracking;
import habit.tracker.habittracker.api.model.tracking.TrackingList;
import habit.tracker.habittracker.api.service.VnHabitApiService;
import habit.tracker.habittracker.common.util.AppGenerator;
import habit.tracker.habittracker.repository.habit.Schedule;
import habit.tracker.habittracker.repository.habit.TrackingDateInWeek;
import habit.tracker.habittracker.common.util.MySharedPreference;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.habit.HabitEntity;
import habit.tracker.habittracker.repository.tracking.TrackingEntity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static habit.tracker.habittracker.adapter.habit.HabitRecyclerViewAdapter.TYPE_ADD;

public class MainActivity extends AppCompatActivity implements HabitRecyclerViewAdapter.ItemClickListener {
    public static final int CREATE_NEW_HABIT = 0;
    public static final int UPDATE_HABIT = 1;
    public static final int USE_FILTER = 2;
    private static final int REPORT = 3;

    public static final String HABIT_ID = "habit_id";
    public static final String HABIT_COLOR = "habit_color";

    List<TrackingItem> trackingItemList = new ArrayList<>();
    HabitRecyclerViewAdapter trackingAdapter;
    String currentDate;
    String firstCurrentDate;
    int dayStack = 0;

    @BindView(R.id.rvMenu)
    RecyclerView recyclerView;

    @BindView(R.id.imgFilter)
    ImageView imgFilter;

    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.imgNext)
    ImageView imgNext;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.report)
    View btnReport;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CREATE_NEW_HABIT || requestCode == UPDATE_HABIT) {

            if (resultCode == RESULT_OK) {
                loadData(true);
            }

        } else if (requestCode == USE_FILTER) {
            if (resultCode == RESULT_OK) {
                Bundle filter = data.getExtras();
                String type = filter.getString("type");
                String target = filter.getString("target");
                String group = filter.getString("group");

                List<TrackingItem> filteredList = new ArrayList<>();
                for (int i = 0; i < trackingItemList.size(); i++) {
                    if ((target.equals("-1") || target.equals(trackingItemList.get(i).getTarget()))
                            && (type.equals("-1") || type.equals(String.valueOf(trackingItemList.get(i).getHabitType())))
                            && (group.equals("-1") || group.equals(trackingItemList.get(i).getGroup()))
                            ) {
                        filteredList.add(trackingItemList.get(i));
                    }
                }
                trackingAdapter.setData(filteredList);
                trackingAdapter.notifyDataSetChanged();

            }
        } else if (requestCode == REPORT) {
            if (currentDate.equals(firstCurrentDate)) {
                loadData(true);
            } else {
                loadData(false);
                updateData(trackingItemList, trackingAdapter, currentDate);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        currentDate = AppGenerator.getCurrentDate(AppGenerator.YMD_SHORT);
        firstCurrentDate = currentDate;

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        trackingAdapter = new HabitRecyclerViewAdapter(MainActivity.this, trackingItemList);
        trackingAdapter.setClickListener(MainActivity.this);
        recyclerView.setAdapter(trackingAdapter);

        loadData(true);
    }

    private void loadData(final boolean display) {
        trackingItemList.clear();

        String userId = MySharedPreference.getUserId(this);

        VnHabitApiService mService = VnHabitApiUtils.getApiService();
        mService.getHabit(userId).enqueue(new Callback<HabitResponse>() {
            @Override
            public void onResponse(Call<HabitResponse> call, Response<HabitResponse> response) {
                if (response.body().getResult().equals("1")) {

                    Database db = Database.getInstance(MainActivity.this);
                    db.open();

                    List<Habit> habitList = response.body().getHabit();

                    for (Habit habit : habitList) {

                        Calendar ca = Calendar.getInstance();
                        ca.setTimeInMillis(System.currentTimeMillis());
                        int year = ca.get(Calendar.YEAR);
                        int month = ca.get(Calendar.MONTH) + 1;
                        int date = ca.get(Calendar.DATE);
                        String currentDate = year + "-" + month + "-" + date;
                        currentDate = AppGenerator.format(currentDate, AppGenerator.YMD_SHORT, AppGenerator.YMD_SHORT);

                        if (isTodayHabit(year, month - 1, date, habit)) {

                            // update tracking data from server
                            for (Tracking track : habit.getTracksList()) {
                                Database.trackingImpl.saveTracking(Database.trackingImpl.convert(track));
                            }

                            // get today tracking record
                            TrackingEntity todayTracking = getTodayTracking(habit.getHabitId(), currentDate, 0);
                            TrackingItem item = new TrackingItem(
                                    todayTracking.getTrackingId(),
                                    habit.getHabitId(),
                                    habit.getHabitName(),
                                    habit.getHabitDescription(),
                                    habit.getHabitType(),
                                    Integer.parseInt(habit.getMonitorType()),
                                    habit.getMonitorNumber(),
                                    Integer.parseInt(todayTracking.getCount()),
                                    habit.getMonitorUnit(),
                                    habit.getHabitColor());
                            item.setTarget(habit.getHabitTarget());
                            item.setGroup(habit.getGroupId());

                            if (display) {
                                trackingItemList.add(item);
                            }
                        }
                    }

                    for (Habit habit : habitList) {
                        Database.habitDaoImpl.saveUpdateHabit(Database.habitDaoImpl.convert(habit));
                    }
                    db.close();
                }

                if (display) {
                    trackingAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<HabitResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void onTrackingValueChanged(View view, int type, int position, int count) {
        // update UI
        TrackingItem item = trackingItemList.get(position);
        item.setCount(count);

        Database db = Database.getInstance(this);
        db.open();
        TrackingList trackingData = new TrackingList();
        Tracking tracking = new Tracking();
        tracking.setTrackingId(item.getTrackId());
        tracking.setHabitId(item.getHabitId());
        tracking.setCurrentDate(this.currentDate);
        tracking.setCount(String.valueOf(item.getCount()));
        trackingData.getTrackingList().add(tracking);
        if (!Database.trackingImpl
                .updateTracking(Database.trackingImpl.convert(tracking))) {
            return;
        }
        db.close();

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

    @Override
    public void onItemClick(View view, int type, int position) {
        if (TYPE_ADD == type) {
            Intent intent = new Intent(this, HabitActivity.class);
            startActivityForResult(intent, CREATE_NEW_HABIT);
        } else {
            Intent intent = new Intent(this, ReportDetailsActivity.class);
            intent.putExtra(HABIT_ID, trackingItemList.get(position).getHabitId());
            intent.putExtra(HABIT_COLOR, trackingItemList.get(position).getColor());
            startActivityForResult(intent, REPORT);
        }
    }

    public void updateData(List<TrackingItem> trackingItemList, HabitRecyclerViewAdapter trackingAdapter, String currentDate) {
        String[] arr = currentDate.split("-");
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int date = Integer.parseInt(arr[2]);
        Schedule schedule = new Schedule(year, month, date);

        Database db = Database.getInstance(this);
        db.open();
        List<HabitEntity> habitEntities = Database.getHabitDb().getTodayHabit(schedule, currentDate);

        boolean isDataSetChanged = false;
        for (HabitEntity habit : habitEntities) {

            // get tracking records on current date
            TrackingEntity record = Database.getTrackingDb()
                    .getTracking(habit.getHabitId(), currentDate);

            if (record == null) {
                record = getTodayTracking(habit.getHabitId(), currentDate, 0);
                Database.getTrackingDb().saveTracking(record);
            }
            TrackingItem item = new TrackingItem(
                    record.getTrackingId(),
                    habit.getHabitId(),
                    habit.getHabitName(),
                    habit.getHabitDescription(),
                    habit.getHabitType(),
                    Integer.parseInt(habit.getMonitorType()),
                    habit.getMonitorNumber(),
                    Integer.parseInt(record.getCount()),
                    habit.getMonitorUnit(),
                    habit.getHabitColor());
            item.setTarget(habit.getHabitTarget());
            item.setGroup(habit.getGroupId());
            trackingItemList.add(item);
            if (!isDataSetChanged) {
                isDataSetChanged = true;
            }
        }
        db.close();

        trackingAdapter.setEditable(currentDate.compareTo(firstCurrentDate) < 1);
        trackingAdapter.notifyDataSetChanged();
    }

    public TrackingEntity getTodayTracking(String habitId, String currentDate, int defaultVal) {
        TrackingEntity todayTracking = Database.trackingImpl
                .getTracking(habitId, currentDate);

        if (todayTracking == null) {
            todayTracking = new TrackingEntity();
            todayTracking.setTrackingId(AppGenerator.getNewId());
            todayTracking.setHabitId(habitId);
            todayTracking.setCount(String.valueOf(defaultVal));
            todayTracking.setCurrentDate(currentDate);
            todayTracking.setDescription(currentDate);
            Database.trackingImpl.saveTracking(todayTracking);
        }
        return todayTracking;
    }

    @OnClick(R.id.imgNext)
    public void loadNextDateHabit(View v) {
        String nextDate = AppGenerator.getNextDate(currentDate, AppGenerator.YMD_SHORT);
        if (nextDate != null) {
            dayStack++;
            updateTitle(nextDate);
            currentDate = nextDate;
            trackingItemList.clear();
            updateData(trackingItemList, trackingAdapter, currentDate);
        }
    }

    @OnClick(R.id.imgBack)
    public void loadPreDateHabit(View v) {
        String preDate = AppGenerator.getPreDate(currentDate, AppGenerator.YMD_SHORT);
        if (preDate != null) {
            dayStack--;
            updateTitle(preDate);
            currentDate = preDate;
            trackingItemList.clear();
            updateData(trackingItemList, trackingAdapter, currentDate);
        }
    }

    public void backToCurrent(View v) {
        dayStack = 0;
        updateTitle(firstCurrentDate);
        currentDate = firstCurrentDate;
        trackingItemList.clear();
        updateData(trackingItemList, trackingAdapter, currentDate);
    }

    @OnClick(R.id.report)
    public void report(View v) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.imgFilter)
    public void showFilter(View v) {
        Intent intent = new Intent(this, FilterMainActivity.class);
        startActivityForResult(intent, USE_FILTER);
    }

    public void showEmpty(View v) {
        Intent intent = new Intent(this, EmptyActivity.class);
        startActivity(intent);
    }

    public boolean isTodayHabit(int year, int month, int date, TrackingDateInWeek habit) {
        Calendar ca = Calendar.getInstance();
        ca.set(year, month, date);
        int toDay = ca.get(Calendar.DAY_OF_WEEK);
        return toDay == Calendar.MONDAY && habit.getMon() != null && habit.getMon().equals("1")
                || toDay == Calendar.TUESDAY && habit.getTue() != null && habit.getTue().equals("1")
                || toDay == Calendar.WEDNESDAY && habit.getWed() != null && habit.getWed().equals("1")
                || toDay == Calendar.THURSDAY && habit.getThu() != null && habit.getThu().equals("1")
                || toDay == Calendar.FRIDAY && habit.getFri() != null && habit.getFri().equals("1")
                || toDay == Calendar.SATURDAY && habit.getSat() != null && habit.getSat().equals("1")
                || toDay == Calendar.SUNDAY && habit.getSun() != null && habit.getSun().equals("1");
    }

    private void updateTitle(String date) {
        if (dayStack == 0) {
            tvDate.setText("Hôm nay");
        } else if (dayStack == 1) {
            tvDate.setText("Ngày mai");
        } else if (dayStack == -1) {
            tvDate.setText("Hôm qua");
        }
        else {
            tvDate.setText(AppGenerator.format(date, AppGenerator.YMD_SHORT, AppGenerator.DMY_SHORT));
        }
    }
}
