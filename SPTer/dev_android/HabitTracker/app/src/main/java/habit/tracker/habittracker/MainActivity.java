package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import habit.tracker.habittracker.api.VnHabitApiUtils;
import habit.tracker.habittracker.api.model.habit.Habit;
import habit.tracker.habittracker.api.model.habit.HabitResponse;
import habit.tracker.habittracker.api.service.VnHabitApiService;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.habit.HabitEntity;
import habit.tracker.habittracker.repository.tracking.TrackingEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_ADD;
import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_CHECK;
import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_COUNT;

public class MainActivity extends AppCompatActivity implements MenuRecyclerViewAdapter.ItemClickListener {
    public static final int CREATE_NEW_HABIT = 0;
    public static final int UPDATE_HABIT = 1;
    public static final String HABIT_ID = "HABIT_ID";
    List<TrackingItem> data = new ArrayList<>();
    MenuRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initScreen();
    }

    @Override
    public void onSetCount(View view, int type, int position, int count) {
        data.get(position).setCount(count);
        MySharedPreference.save(this, data.get(position).getHabitId(), "hb", String.valueOf(count));
        if (TYPE_COUNT == type) {

        } else if (TYPE_CHECK == type) {

        }
    }

    @Override
    public void onItemClick(View view, int type, int position) {
        if (TYPE_ADD == type) {
            Intent intent = new Intent(this, HabitActivity.class);
            startActivityForResult(intent, CREATE_NEW_HABIT);
        } else {
            Intent intent = new Intent(this, HabitActivity.class);
            intent.putExtra(HABIT_ID, data.get(position).getHabitId());
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
        String userId = MySharedPreference.getUserId(this);
        VnHabitApiService mService = VnHabitApiUtils.getApiService();
        mService.getHabit(userId).enqueue(new Callback<HabitResponse>() {
            @Override
            public void onResponse(Call<HabitResponse> call, Response<HabitResponse> response) {
                RecyclerView recyclerView = findViewById(R.id.rvMenu);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                data.clear();
                adapter = new MenuRecyclerViewAdapter(MainActivity.this, data);
                adapter.setClickListener(MainActivity.this);
                recyclerView.setAdapter(adapter);

                if (response.body().getResult().equals("1")) {
                    Database db = new Database(MainActivity.this);
                    db.open();

                    List<Habit> res = response.body().getHabit();
                    List<HabitEntity> entities = new ArrayList<>();
                    for (Habit habit : res) {

                        Calendar ca = Calendar.getInstance();
                        String currentDate = ca.get(Calendar.YEAR) + "-" + ca.get(Calendar.MONTH) + "-" + ca.get(Calendar.DATE);
                        TrackingEntity tracking = Database.sTrackingImpl.getTracking(habit.getHabitId(), currentDate);
                        if (tracking.getTrackingId() == null) {
                            tracking.setHabitId(habit.getHabitId());
                            tracking.setCount("0");
                            tracking.setCurrentDate(currentDate);
                            tracking.setDescription(currentDate);
                            Database.sTrackingImpl.saveTracking(tracking);
                        }

                        TrackingItem item = new TrackingItem(
                                habit.getHabitId(),
                                habit.getHabitName(),
                                habit.getHabitDescription(),
                                habit.getHabitType(),
                                Integer.parseInt(habit.getMonitorType()),
                                habit.getMonitorNumber(),
                                Integer.parseInt(tracking.getCount()),
                                habit.getMonitorUnit(),
                                habit.getHabitColor());
                        item.setTrackId(Database.sTrackingImpl.getLastId());
                        data.add(item);
                        entities.add(Database.sHabitDaoImpl.convert(habit));
                    }
                    adapter.notifyDataSetChanged();

                    // update db
                    for (HabitEntity entity : entities) {
                        Database.sHabitDaoImpl.saveHabit(entity);
                    }
                    db.close();
                }
            }

            @Override
            public void onFailure(Call<HabitResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Not OK", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showEmpty(View v) {
        Intent itent = new Intent(this, EmptyActivity.class);
        startActivity(itent);
    }

    @Override
    protected void onStop() {
        TrackingEntity entity = new TrackingEntity();
        TrackingItem item;
        Database db = new Database(MainActivity.this);
        db.open();
        for (int i = 0; i < data.size(); i++) {
            item = data.get(i);
            entity.setTrackingId(item.getHabitId());
            entity.setCount(String.valueOf(item.getCount()));
            Database.sTrackingImpl.updateTracking(entity);
        }
        db.close();
        super.onStop();
    }
}
