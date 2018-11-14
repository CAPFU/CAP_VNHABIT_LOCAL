package habit.tracker.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import habit.tracker.habittracker.adapter.RecyclerViewItemClickListener;
import habit.tracker.habittracker.adapter.habitsuggestion.SuggestRecylViewAdapter;
import habit.tracker.habittracker.api.model.search.HabitSuggestion;

public class SuggestionActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    @BindView(R.id.rvSuggestion)
    RecyclerView rvSuggestion;

    List<HabitSuggestion> data;
    SuggestRecylViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        ButterKnife.bind(this);

//        data = new ArrayList<>();
//        data.add(new HabitSuggestion("Sức khỏe", true));
//        data.add(new HabitSuggestion("1", "Chạy bộ", "chay bo", "12", false));
//        data.add(new HabitSuggestion("2", "Hít đất", "hit dat", "16", false));
//        data.add(new HabitSuggestion("3", "Đạp xe", "dap xe", "7", false));
//        data.add(new HabitSuggestion("Học tập", true));
//        data.add(new HabitSuggestion("4", "Học Tiếng Anh", "", "2", false));
//        data.add(new HabitSuggestion("5", "Học Tiếng Hàn", "", "8", false));
//        data.add(new HabitSuggestion("6", "Học Tiếng Nhật", "", "19", false));
//
//        adapter = new SuggestRecylViewAdapter(this, data, this);
//        rvSuggestion.setLayoutManager(new LinearLayoutManager(this));
//        rvSuggestion.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
