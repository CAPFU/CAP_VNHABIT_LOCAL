package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import habit.tracker.habittracker.api.ApiUtils;
import habit.tracker.habittracker.api.model.habit.Habit;
import habit.tracker.habittracker.api.model.habit.HabitResponse;
import habit.tracker.habittracker.api.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_ADD;
import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_CHECK;
import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_COUNT;

public class MainActivity extends AppCompatActivity implements MenuRecyclerViewAdapter.ItemClickListener {

    List<MenuItem> data = new ArrayList<>();
    MenuRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MenuItem item = new MenuItem("đọc sách", "thói quen đọc 4 cuốn sách trong", "tháng này", TYPE_COUNT, "4", "0", "cuốn", R.color.color1);
//        data.add(item);
//        item = new MenuItem("chạy bộ", "chạy bộ 10km trong", "tuần này", TYPE_COUNT, "10", "0", "km", R.color.color2);
//        data.add(item);
//        item = new MenuItem("hít đất", "hít đất 100 cái trong", "hôm nay", TYPE_COUNT, "100", "0", "cái", R.color.color3);
//        data.add(item);
//        item = new MenuItem("đưa gia đình đi du lịch", "đưa gia đình đi du lịch trong", "năm nay", TYPE_CHECK, "2", "0" +
//                "", "lần", R.color.color4);
//        data.add(item);
//        item = new MenuItem("đi mua sắm", "đi mua sắm với vợ trong", "tuần này", TYPE_CHECK, "1", "0", "", R.color.color5);
//        data.add(item);
//        item = new MenuItem("ghi chép chi tiêu", "hãy ghi chép chi tiêu cá nhân", "hôm nay", TYPE_CHECK, "1", "0", "", R.color.color6);
//        data.add(item);

        String userId = MySharedPreference.getUserId(this);
        ApiService mService = ApiUtils.getApiService();
        mService.getHabit(userId).enqueue(new Callback<HabitResponse>() {
            @Override
            public void onResponse(Call<HabitResponse> call, Response<HabitResponse> response) {
                if (response.body().getResult().equals("1")) {
                    List<Habit> res = response.body().getHabit();
                    for (Habit habit : res) {
                        MenuItem item = new MenuItem(habit.getHabitName(),
                                habit.getHabitDescription(),
                                habit.getGoalTime(),
                                Integer.parseInt(habit.getCountType()),
                                habit.getGoalNumber(),
                                "0",
                                habit.getUnit(),
                                habit.getHabitColor());
                        data.add(item);
                    }
                    RecyclerView recyclerView = findViewById(R.id.rvMenu);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new MenuRecyclerViewAdapter(MainActivity.this, data);
                    adapter.setClickListener(MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<HabitResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Not OK", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int type, int position) {
        if (TYPE_ADD == type) {
            Intent intent = new Intent(this, HabitActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, EmptyActivity.class);
            startActivity(intent);
        }
    }
}
