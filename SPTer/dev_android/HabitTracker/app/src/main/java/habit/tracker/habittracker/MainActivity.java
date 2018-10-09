package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_ADD;
import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_CHECK;
import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_COUNT;

public class MainActivity extends AppCompatActivity implements MenuRecyclerViewAdapter.ItemClickListener {

    MenuRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<MenuItem> data = new ArrayList<>();
        MenuItem item = new MenuItem("đọc sách", "thói quen đọc 4 cuốn sách trong", "tháng này", TYPE_COUNT, "4", "0", "cuốn", R.color.color1);
        data.add(item);
        item = new MenuItem("chạy bộ", "chạy bộ 10km trong", "tuần này", TYPE_COUNT, "10", "0", "km", R.color.color2);
        data.add(item);
        item = new MenuItem("hít đất", "hít đất 100 cái trong", "hôm nay", TYPE_COUNT, "100", "0", "cái", R.color.color3);
        data.add(item);
        item = new MenuItem("đưa gia đình đi du lịch", "đưa gia đình đi du lịch trong", "năm nay", TYPE_CHECK, "2", "0" +
                "", "lần", R.color.color4);
        data.add(item);
        item = new MenuItem("đi mua sắm", "đi mua sắm với vợ trong", "tuần này", TYPE_CHECK, "1", "0", "", R.color.color5);
        data.add(item);
        item = new MenuItem("ghi chép chi tiêu", "hãy ghi chép chi tiêu cá nhân", "hôm nay", TYPE_CHECK, "1", "0", "", R.color.color6);
        data.add(item);

        RecyclerView recyclerView = findViewById(R.id.rvMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
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
