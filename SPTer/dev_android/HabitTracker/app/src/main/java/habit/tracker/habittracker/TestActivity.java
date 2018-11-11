package habit.tracker.habittracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import habit.tracker.habittracker.adapter.RecyclerViewItemClickListener;
import habit.tracker.habittracker.adapter.habitsuggestion.HabitSuggestRecylViewAdapter;
import habit.tracker.habittracker.api.VnHabitApiUtils;
import habit.tracker.habittracker.api.model.search.HabitSuggestion;
import habit.tracker.habittracker.api.model.search.SearchResponse;
import habit.tracker.habittracker.api.service.VnHabitApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static habit.tracker.habittracker.common.AppContrant.RES_OK;

public class TestActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    @BindView(R.id.edit_habitName)
    EditText editHabitName;

    @BindView(R.id.rvHabitSuggestion)
    RecyclerView rvHabitSuggestion;
    HabitSuggestRecylViewAdapter suggestAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<HabitSuggestion> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        suggestAdapter = new HabitSuggestRecylViewAdapter(this, data, this);
        rvHabitSuggestion.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rvHabitSuggestion.setLayoutManager(mLayoutManager);
        rvHabitSuggestion.setAdapter(suggestAdapter);

        editHabitName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                VnHabitApiService mService = VnHabitApiUtils.getApiService();
                mService.searchHabitName(s.toString().toLowerCase()).enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        data.clear();
                        if (response.body().getResult().equals(RES_OK)) {
                            for (HabitSuggestion sg : response.body().getSearchResult()) {
                                data.add(new HabitSuggestion(sg.getHabitNameId(), sg.getHabitNameUni(), sg.getHabitName(), sg.getHabitNameCount()));
                            }
                        }
                        suggestAdapter.notifyDataSetChanged();
                        if (data.size() == 0) {

                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        editHabitName.setText(data.get(position).getHabitNameUni());
    }
}
