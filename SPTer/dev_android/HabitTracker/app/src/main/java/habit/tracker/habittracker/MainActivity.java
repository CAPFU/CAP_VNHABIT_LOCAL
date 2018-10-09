package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_CHECK;
import static habit.tracker.habittracker.MenuRecyclerViewAdapter.TYPE_COUNT;

public class MainActivity extends AppCompatActivity implements MenuRecyclerViewAdapter.ItemClickListener {

    MenuRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<MenuItem> data = new ArrayList<>();
        MenuItem item = new MenuItem("đọc sách", "thói quen đọc 4 cuốn sách", "tháng này", TYPE_COUNT, "4", "2", "cuốn", 1);
        data.add(item);
        item = new MenuItem("đọc sách", "thói quen đọc 4 cuốn sách", "tháng này", TYPE_COUNT, "4", "2", "cuốn", 1);
        data.add(item);
        item = new MenuItem("đọc sách", "thói quen đọc 4 cuốn sách", "tháng này", TYPE_CHECK, "4", "2", "cuốn", 1);
        data.add(item);
        item = new MenuItem("đọc sách", "thói quen đọc 4 cuốn sách", "tháng này", TYPE_COUNT, "4", "2", "cuốn", 1);
        data.add(item);
        item = new MenuItem("đọc sách", "thói quen đọc 4 cuốn sách", "tháng này", TYPE_CHECK, "4", "2", "cuốn", 1);
        data.add(item);

        RecyclerView recyclerView = findViewById(R.id.rvMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, EmptyActivity.class);
        startActivity(intent);
    }
}
