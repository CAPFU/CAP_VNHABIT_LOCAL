package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import habit.tracker.habittracker.api.VnHabitApiUtils;
import habit.tracker.habittracker.api.model.habit.Habit;
import habit.tracker.habittracker.api.model.habit.HabitResponse;
import habit.tracker.habittracker.api.model.tracking.Tracking;
import habit.tracker.habittracker.api.model.tracking.TrackingList;
import habit.tracker.habittracker.api.service.VnHabitApiService;
import habit.tracker.habittracker.common.Generator;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.habit.DateTracking;
import habit.tracker.habittracker.repository.habit.HabitEntity;
import habit.tracker.habittracker.repository.tracking.TrackingEntity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static habit.tracker.habittracker.HabitRecyclerViewAdapter.TYPE_ADD;

public class MainActivity extends AppCompatActivity implements HabitRecyclerViewAdapter.ItemClickListener {
    public static final int CREATE_NEW_HABIT = 0;
    public static final int UPDATE_HABIT = 1;
    public static final String HABIT_ID = "HABIT_ID";
    List<TrackingItem> trackingItemList = new ArrayList<>();
    HabitRecyclerViewAdapter trackingAdapter;
    String currentDate;
    @BindView(R.id.imgNext)
    ImageView imgNext;
    @BindView(R.id.imgBack)
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Calendar ca = Calendar.getInstance();
        currentDate = ca.get(Calendar.YEAR) + "-" + (ca.get(Calendar.MONTH) + 1) + "-" + ca.get(Calendar.DATE);
        initScreen();
    }

    @Override
    public void onSetCount(View view, int type, int position, int count) {
        trackingItemList.get(position).setCount(count);
        trackingAdapter.notifyItemChanged(position);
    }

    @Override
    public void onItemClick(View view, int type, int position) {
        if (TYPE_ADD == type) {
            Intent intent = new Intent(this, HabitActivity.class);
            startActivityForResult(intent, CREATE_NEW_HABIT);
        } else {
            Intent intent = new Intent(this, HabitActivity.class);
            intent.putExtra(HABIT_ID, trackingItemList.get(position).getHabitId());
            startActivityForResult(intent, UPDATE_HABIT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CREATE_NEW_HABIT || requestCode == UPDATE_HABIT) {
            if (resultCode == RESULT_OK) {
                initScreen();
            }
        }
    }

    private void initScreen() {
        RecyclerView recyclerView = findViewById(R.id.rvMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        trackingItemList.clear();
        trackingAdapter = new HabitRecyclerViewAdapter(MainActivity.this, trackingItemList);
        trackingAdapter.setClickListener(MainActivity.this);
        recyclerView.setAdapter(trackingAdapter);
        String userId = MySharedPreference.getUserId(this);
        VnHabitApiService mService = VnHabitApiUtils.getApiService();
        mService.getHabit(userId).enqueue(new Callback<HabitResponse>() {
            @Override
            public void onResponse(Call<HabitResponse> call, Response<HabitResponse> response) {
                if (response.body().getResult().equals("1")) {
                    Database db = new Database(MainActivity.this);
                    db.open();
                    List<Habit> habitList = response.body().getHabit();
                    List<HabitEntity> habitEntities = new ArrayList<>();
                    for (Habit habit : habitList) {
                        habitEntities.add(Database.sHabitDaoImpl.convert(habit));
                    }
                    loadData(habitList);
                    trackingAdapter.notifyDataSetChanged();
                    // update db
                    for (HabitEntity entity : habitEntities) {
                        Database.sHabitDaoImpl.saveHabit(entity);
                    }
                    db.close();
                }
            }

            @Override
            public void onFailure(Call<HabitResponse> call, Throwable t) {
                Database db = new Database(MainActivity.this);
                db.open();
//                List<HabitEntity> habitEntities = Database.sHabitDaoImpl.fetchHabit();
//                loadData(habitEntities);
//                trackingAdapter.notifyDataSetChanged();
                db.close();
            }
        });
    }

    public void loadData(List<Habit> habitList) {
        trackingItemList.clear();
        for (Habit habit : habitList) {
            Calendar ca = Calendar.getInstance();
            String currentDate = ca.get(Calendar.YEAR) + "-" + (ca.get(Calendar.MONTH) + 1) + "-" + ca.get(Calendar.DATE);
            int day = ca.get(Calendar.DAY_OF_WEEK);
            if (day == Calendar.MONDAY && habit.getMon() != null && habit.getMon().equals("1")
                    || day == Calendar.TUESDAY && habit.getTue() != null && habit.getTue().equals("1")
                    || day == Calendar.WEDNESDAY && habit.getWed() != null && habit.getWed().equals("1")
                    || day == Calendar.THURSDAY && habit.getThu() != null && habit.getThu().equals("1")
                    || day == Calendar.FRIDAY && habit.getFri() != null && habit.getFri().equals("1")
                    || day == Calendar.SATURDAY && habit.getSat() != null && habit.getSat().equals("1")
                    || day == Calendar.SUNDAY && habit.getSun() != null && habit.getSun().equals("1")) {

                Tracking tracking = new Tracking();
                for (Tracking track : habit.getTracksList()) {
                    Database.sTrackingImpl.saveTracking(Database.sTrackingImpl.convert(track));
                }

                TrackingEntity todayTracking = Database.sTrackingImpl.getTracking(habit.getHabitId(), currentDate);
                if (todayTracking.getTrackingId() == null) {
                    todayTracking.setTrackingId(Generator.getNewId());
                    todayTracking.setHabitId(habit.getHabitId());
                    todayTracking.setCount("0");
                    todayTracking.setCurrentDate(currentDate);
                    todayTracking.setDescription(currentDate);
                    Database.sTrackingImpl.saveTracking(todayTracking);
                }

                TrackingItem item = new TrackingItem(
                        habit.getHabitId(),
                        habit.getHabitName(),
                        habit.getHabitDescription(),
                        habit.getHabitType(),
                        Integer.parseInt(habit.getMonitorType()),
                        habit.getMonitorNumber(),
                        Integer.parseInt(todayTracking.getCount()),
                        habit.getMonitorUnit(),
                        habit.getHabitColor());

                item.setTrackId(todayTracking.getTrackingId());
                trackingItemList.add(item);
            }
        }
    }

    public void showEmpty(View v) {
        Intent intent = new Intent(this, EmptyActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.imgNext)
    public void next(ImageView img) {
        String nextDate = Generator.getNextDate(currentDate);
        if (nextDate != null) {
            currentDate = nextDate;
            Toast.makeText(this, nextDate, Toast.LENGTH_SHORT).show();
            Database db = new Database(this);
            db.open();
            trackingItemList.clear();
            updateDataList(trackingItemList, trackingAdapter);
            db.close();
        }
    }

    @OnClick(R.id.imgBack)
    public void back(ImageView img) {
        String preDate = Generator.getPreDate(currentDate);
        if (preDate != null) {
            currentDate = preDate;
            Toast.makeText(this, preDate, Toast.LENGTH_SHORT).show();
            Database db = new Database(this);
            db.open();
            trackingItemList.clear();
            updateDataList(trackingItemList, trackingAdapter);
            db.close();
        }
    }

    public void updateDataList(List<TrackingItem> trackingItemList, HabitRecyclerViewAdapter trackingAdapter) {
        List<DateTracking> dateTrackingList = Database.sHabitDaoImpl.getHabitOnTrackingDay(currentDate);
        HabitEntity habitEntity;
        TrackingEntity trackingEntity;
        if (dateTrackingList.size() > 0) {
            for (DateTracking tracking : dateTrackingList) {
                habitEntity = tracking.getHabitEntity();
                trackingEntity = tracking.getTrackingEntity();
                TrackingItem item = new TrackingItem(
                        habitEntity.getHabitId(),
                        habitEntity.getHabitName(),
                        habitEntity.getHabitDescription(),
                        habitEntity.getHabitType(),
                        Integer.parseInt(habitEntity.getMonitorType()),
                        habitEntity.getMonitorNumber(),
                        Integer.parseInt(trackingEntity.getCount()),
                        habitEntity.getMonitorUnit(),
                        habitEntity.getHabitColor());
                item.setTrackId(trackingEntity.getTrackingId());
                trackingItemList.add(item);
            }
            trackingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        VnHabitApiService service = VnHabitApiUtils.getApiService();
        List<Tracking> trackList = new ArrayList<>();
        TrackingList trackingData = new TrackingList();
        Tracking tracking;
        for (TrackingItem item : trackingItemList) {
            tracking = new Tracking();
            tracking.setTrackingId(item.getTrackId());
            tracking.setHabitId(item.getHabitId());
            tracking.setCurrentDate(currentDate);
            tracking.setCount(String.valueOf(item.getCount()));
            trackList.add(tracking);
        }
        trackingData.setTrackingList(trackList);
        service.replace(trackingData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Không tải được dữ liệu.", Toast.LENGTH_SHORT).show();
            }
        });

        TrackingItem item;
        Database db = new Database(MainActivity.this);
        db.open();
        for (int i = 0; i < trackingItemList.size(); i++) {
            item = trackingItemList.get(i);
            if (!Database.sTrackingImpl.updateTrackCount(item.getTrackId(), String.valueOf(item.getCount()))) {
                break;
            }
        }
        db.close();
        super.onPause();
    }

}
