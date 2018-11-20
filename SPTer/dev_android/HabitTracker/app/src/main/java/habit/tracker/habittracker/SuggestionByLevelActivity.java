package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import habit.tracker.habittracker.adapter.RecyclerViewItemClickListener;
import habit.tracker.habittracker.adapter.suggestion.SuggestByGroupAdapter;
import habit.tracker.habittracker.api.VnHabitApiUtils;
import habit.tracker.habittracker.api.model.search.HabitSuggestion;
import habit.tracker.habittracker.api.model.suggestion.SuggestByLevelReponse;
import habit.tracker.habittracker.api.service.VnHabitApiService;
import habit.tracker.habittracker.common.util.AppGenerator;
import habit.tracker.habittracker.common.util.MySharedPreference;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.user.UserEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionByLevelActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    public static String SUGGEST_NAME = "SuggestionByLevelActivity.pick_name";
    public static String SUGGEST_NAME_ID = "SuggestionByLevelActivity.suggest_name_id";


    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvStartedDate)
    TextView tvStartedDate;
    @BindView(R.id.tvContinueUsing)
    TextView tvContinueUsing;
    @BindView(R.id.tvLevel)
    TextView tvLevel;
    @BindView(R.id.tvUserScore)
    TextView tvUserScore;
    @BindView(R.id.tvBestContinue)
    TextView tvBestContinue;
    @BindView(R.id.tvCurrentContinue)
    TextView tvCurrentContinue;

    @BindView(R.id.rvSuggestion)
    RecyclerView rvSuggestion;

    UserEntity userEntity;
    List<HabitSuggestion> displaySuggestList = new ArrayList<>();
    SuggestByGroupAdapter suggestByGroupAdapter;
    VnHabitApiService mService = VnHabitApiUtils.getApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_suggestion_by_level);
        ButterKnife.bind(this);

        mService.getHabitSuggestByLevel().enqueue(new Callback<SuggestByLevelReponse>() {
            @Override
            public void onResponse(Call<SuggestByLevelReponse> call, Response<SuggestByLevelReponse> response) {
                if (response.body().getResult().equals("1")) {
                    // 0: low, 1: med, 2: hig
                    List<List<HabitSuggestion>> data = response.body().getData();
                    List<HabitSuggestion> curLevl;
                    String[] level = new String[]{"Thói quen dễ được nhiều người chọn", "Thói quen trung bình được nhiều người chọn", "Thói quen khó được nhiều người chọn"};

                    Database db = Database.getInstance(SuggestionByLevelActivity.this);
                    db.open();
                    userEntity = Database.getUserDb().getUser(MySharedPreference.getUserId(SuggestionByLevelActivity.this));
                    db.close();
                    int pendDate = AppGenerator.countDayBetween(userEntity.getCreatedDate(), AppGenerator.getCurrentDate(AppGenerator.YMD_SHORT));
                    if (pendDate >= 30) {
                        level[2] = "Thói quen khó được nhiều người chọn (khuyên chọn)";
                    } else {
                        level[0] = "Thói quen dễ được nhiều người chọn (khuyên chọn)";
                    }

                    for (int i = 0; i < data.size(); i++) {
                        displaySuggestList.add(new HabitSuggestion(null, level[i], true));
                        curLevl = data.get(i);
                        for (int j = 0; j < curLevl.size(); j++) {
                            displaySuggestList.add(new HabitSuggestion(
                                    curLevl.get(j).getHabitNameId(),
                                    curLevl.get(j).getGroupId(),
                                    curLevl.get(j).getHabitNameUni(),
                                    curLevl.get(j).getHabitName(),
                                    curLevl.get(j).getHabitNameCount(),
                                    false
                            ));
                        }
                    }
                    suggestByGroupAdapter = new SuggestByGroupAdapter(SuggestionByLevelActivity.this, displaySuggestList, SuggestionByLevelActivity.this);
                    rvSuggestion.setLayoutManager(new LinearLayoutManager(SuggestionByLevelActivity.this));
                    rvSuggestion.setAdapter(suggestByGroupAdapter);
                    tvUsername.setText(userEntity.getUsername());
                    tvStartedDate.setText(AppGenerator.format(userEntity.getCreatedDate(), AppGenerator.YMD_SHORT, AppGenerator.DMY_SHORT));
                    tvContinueUsing.setText(userEntity.getContinueUsingCount());
                    tvLevel.setText(String.valueOf(getLevel(Integer.parseInt(userEntity.getUserScore()))));
                    tvUserScore.setText(userEntity.getUserScore());
                    tvBestContinue.setText(userEntity.getBestContinueUsingCount());
                    tvCurrentContinue.setText(userEntity.getCurrentContinueUsingCount());
                }
            }

            @Override
            public void onFailure(Call<SuggestByLevelReponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        HabitSuggestion item = displaySuggestList.get(position);
        Intent intent = new Intent(this, HabitActivity.class);
        intent.putExtra(SUGGEST_NAME_ID, item.getHabitNameId());
        intent.putExtra(SUGGEST_NAME, item.getHabitNameUni());
        startActivity(intent);
        finish();
    }

    private int getLevel(int score) {
        if (score < 10) {
            return 1;
        }
        if (score < 20) {
            return 2;
        }
        if (score < 50){
            return 3;
        }
        if (score < 120){
            return 4;
        }
        if (score < 290){
            return 5;
        }
        if (score < 700){
            return 6;
        }
        if (score < 1690){
            return 7;
        }
        if (score < 4080){
            return 8;
        }
        if (score <9850){
            return 9;
        }
        if (score < 23780){
            return 10;
        }
        return 11;
    }
}
