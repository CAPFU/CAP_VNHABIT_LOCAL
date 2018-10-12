package habit.tracker.habittracker;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import habit.tracker.habittracker.api.ApiUtils;
import habit.tracker.habittracker.api.model.habit.Habit;
import habit.tracker.habittracker.api.model.habit.HabitResult;
import habit.tracker.habittracker.api.model.user.UserResponse;
import habit.tracker.habittracker.api.service.ApiService;
import habit.tracker.habittracker.common.Validator;
import habit.tracker.habittracker.common.ValidatorType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitActivity extends AppCompatActivity {

    @BindView(R.id.edit_habit_name)
    TextView tvHabitName;

    @BindView(R.id.g1_btn_build)
    Button btnHabitBuild;
    @BindView(R.id.g1_btn_quit)
    Button btnHabitQuit;
    int habitType = 0;

    View btnWatchMode;
    @BindView(R.id.g2_btn_daily)
    Button btnDaily;
    @BindView(R.id.g2_btn_weekly)
    Button btnWeekly;
    @BindView(R.id.g2_btn_monthly)
    Button btnMonthly;
    @BindView(R.id.g2_btn_yearly)
    Button btnYearly;
    int watchMode = 0;

    @BindView(R.id.tv_count_unit)
    TextView tvCountUnit;
    @BindView(R.id.ck_type_check)
    View ckTypeCheck;
    @BindView(R.id.ck_type_count)
    View ckTypeCount;
    @BindView(R.id.g3_ck_check)
    ImageView imgTypeCheck;
    @BindView(R.id.g3_ck_count)
    ImageView imgTypeCount;
    @BindView(R.id.edit_count)
    EditText editCount;
    int typeCount = 0;

    @BindView(R.id.btn_no_unit)
    Button btnNoUnit;
    @BindView(R.id.btn_set_unit)
    Button btnSetUnit;
    @BindView(R.id.edit_unit)
    EditText editUnit;
    Boolean hasCountUnit = false;

    @BindView(R.id.btnMon)
    TextView btnMon;
    @BindView(R.id.btnTue)
    TextView btnTue;
    @BindView(R.id.btnWed)
    TextView btnWed;
    @BindView(R.id.btnThu)
    TextView btnThu;
    @BindView(R.id.btnFri)
    TextView btnFri;
    @BindView(R.id.btnSat)
    TextView btnSat;
    @BindView(R.id.btnSun)
    TextView btnSun;
    boolean[] watchDate = new boolean[7];

    @BindView(R.id.color1)
    View color1;
    @BindView(R.id.color2)
    View color2;
    @BindView(R.id.color3)
    View color3;
    @BindView(R.id.color4)
    View color4;
    @BindView(R.id.color5)
    View color5;
    @BindView(R.id.color6)
    View color6;
    @BindView(R.id.color7)
    View color7;
    @BindView(R.id.color8)
    View color8;
    @BindView(R.id.color9)
    View color9;
    @BindView(R.id.color10)
    View color10;
    View habitColor;
    String colorCode;
    int[] colors = new int[]{
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9,
            R.color.color10
    };

    @BindView(R.id.ll_start_date)
    View mStartDate;
    @BindView(R.id.ll_end_date)
    View mEndDate;
    @BindView(R.id.check_startDate)
    ImageView chkStartDate;
    @BindView(R.id.check_endDate)
    ImageView chkEndDate;
    boolean[] planDate = new boolean[2];

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.edit_description)
    EditText editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
        ButterKnife.bind(this);

        btnWatchMode = btnDaily;
        color1.setBackground(getCircleBackground(colors[0]));
        color2.setBackground(getCircleBackground(colors[1]));
        color3.setBackground(getCircleBackground(colors[2]));
        color4.setBackground(getCircleBackground(colors[3]));
        color5.setBackground(getCircleBackground(colors[4]));
        color6.setBackground(getCircleBackground(colors[5]));
        color7.setBackground(getCircleBackground(colors[6]));
        color8.setBackground(getCircleBackground(colors[7]));
        color9.setBackground(getCircleBackground(colors[8]));
        color10.setBackground(getCircleBackground(colors[9]));
    }

    @OnClick(R.id.btn_save)
    public void saveHabit(View v) {
        Validator validator = new Validator();
        validator.setErrorMsgListener(new Validator.ErrorMsg() {
            @Override
            public void showError(ValidatorType type, String key) {
                switch (type) {
                    case EMPTY:
                        Toast.makeText(HabitActivity.this, key + " không được rỗng", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        String habitName = tvHabitName.getText().toString();
        String habitType = String.valueOf(this.habitType);
        String userId = MySharedPreference.getUserId(this);
        String categoryId;
        String countType = "0";
        String unit = null;
        if (hasCountUnit) {
            countType = "1";
            unit = editUnit.getText().toString();
        }
        String startDate = null;
        String endDate = null;
        String createdDate = null;
        String color = habitColor.getTag().toString();

        String description = editDescription.getText().toString();
        if (!validator.checkEmpty("Tên thói quen", habitName)) {
            return;
        }

        Habit habit = new Habit();
        habit.setHabitName(habitName);
        habit.setHabitType(habitType);
        habit.setUnit(unit);
        habit.setCountType(countType);
        habit.setStartDate(null);
        habit.setEndDate(null);
        habit.setCreatedDate(null);
        habit.setHabitColor(color);
        habit.setHabitDescription(description);

        ApiService mService = ApiUtils.getApiService();
        mService.addHabit(habit).enqueue(new Callback<HabitResult>() {
            @Override
            public void onResponse(Call<HabitResult> call, Response<HabitResult> response) {
                if (response.body().getResult().equals("1")) {
                    Toast.makeText(HabitActivity.this, "Tạo thói quen thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<HabitResult> call, Throwable t) {
                Toast.makeText(HabitActivity.this, "not ok", Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick({R.id.g1_btn_build, R.id.g1_btn_quit})
    public void setBuildType(View v) {
        setGreenBg(v);
        switch (v.getId()) {
            case R.id.g1_btn_build:
                setWhiteBg(btnHabitQuit);
                habitType = 0;
                break;
            case R.id.g1_btn_quit:
                setWhiteBg(btnHabitBuild);
                habitType = 1;
                break;
        }
    }

    @OnClick({R.id.g2_btn_daily, R.id.g2_btn_weekly, R.id.g2_btn_monthly, R.id.g2_btn_yearly})
    public void setWatchMode(View view) {
        setWhiteBg(btnWatchMode);
        setGreenBg(view);
        btnWatchMode = view;
        watchMode = Integer.parseInt(view.getTag().toString());
        switch (view.getId()) {
            case R.id.g2_btn_daily:
                tvCountUnit.setText("trong một ngày");
                break;
            case R.id.g2_btn_weekly:
                tvCountUnit.setText("trong một tuần");
                break;
            case R.id.g2_btn_monthly:
                tvCountUnit.setText("trong một tháng");
                break;
            case R.id.g2_btn_yearly:
                tvCountUnit.setText("trong một năm");
                break;
        }
    }

    @OnClick({R.id.ck_type_check, R.id.ck_type_count})
    public void checkCountType(View view) {
        if (view.getId() == R.id.ck_type_check) {
            uncheck(imgTypeCount);
            check(imgTypeCheck);
            editCount.setEnabled(false);
            typeCount = 0;
        } else if (view.getId() == R.id.ck_type_count) {
            uncheck(imgTypeCheck);
            check(imgTypeCount);
            editCount.setEnabled(true);
            typeCount = 1;
        }
    }

    @OnClick({R.id.btn_no_unit, R.id.btn_set_unit})
    public void setCountUnit(View v) {
        if (v.getId() == R.id.btn_no_unit) {
            setWhiteBg(btnSetUnit);
            setGreenBg(v);
            editUnit.setEnabled(false);
            hasCountUnit = false;
        } else if (v.getId() == R.id.btn_set_unit) {
            setWhiteBg(btnNoUnit);
            setGreenBg(v);
            editUnit.setEnabled(true);
            hasCountUnit = true;
        }
    }

    @OnClick({R.id.btnMon, R.id.btnTue, R.id.btnWed, R.id.btnThu, R.id.btnFri, R.id.btnSat, R.id.btnSun})
    public void setWatchDate(View v) {
        int tag = Integer.parseInt(v.getTag().toString());
        if (watchDate[tag]) {
            uncheckDate(v);
        } else {
            checkDate(v);
        }
        watchDate[tag] = !watchDate[tag];
    }

    @OnClick({R.id.ll_start_date, R.id.ll_end_date})
    public void setStartDate(View v) {
        if (v.getId() == R.id.ll_start_date) {
            if (planDate[0]) {
                uncheck(chkStartDate);
            } else {
                check(chkStartDate);
            }
            planDate[0] = !planDate[0];
        } else if (v.getId() == R.id.ll_end_date) {
            if (planDate[1]) {
                uncheck(chkEndDate);
            } else {
                check(chkEndDate);
            }
            planDate[1] = !planDate[1];
        }
    }


    @OnClick({R.id.color1,
            R.id.color2,
            R.id.color3,
            R.id.color4,
            R.id.color5,
            R.id.color6,
            R.id.color7,
            R.id.color8,
            R.id.color9,
            R.id.color10})
    public void setHabitColor(View v) {
        int num = 0;
        if (habitColor != null) {
            num = Integer.parseInt(habitColor.getTag().toString());
            unpickColor(habitColor, colors[num]);
        }
        num = Integer.parseInt(v.getTag().toString());
        pickColor(v, colors[num]);
        habitColor = v;
    }

    public void setGreenBg(View v) {
        v.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_green));
    }

    public void setWhiteBg(View v) {
        v.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
    }

    public void check(ImageView img) {
        img.setImageResource(R.drawable.ck_checked);
    }

    public void uncheck(ImageView img) {
        img.setImageResource(R.drawable.ck_unchecked);
    }

    public void checkDate(View v) {
        v.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_circle));
    }

    public void uncheckDate(View v) {
        v.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
    }

    public void pickColor(View v, int color){
        v.setBackground(getCircleCheckBackground(color));
    }

    public void unpickColor(View v, int color){
        v.setBackground(getCircleBackground(color));
    }

    public void showEmpty(View v) {
        Intent intent = new Intent(HabitActivity.this, EmptyActivity.class);
        HabitActivity.this.startActivity(intent);
    }

    public void cancel(View v) {
        finish();
    }

    private Drawable getCircleBackground(int color) {
        Drawable mDrawable = ContextCompat.getDrawable(this, R.drawable.bg_circle);
        if (mDrawable != null) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(this.getResources().getColor(color), PorterDuff.Mode.MULTIPLY));
        }
        return mDrawable;
    }

    private Drawable getCircleCheckBackground(int color) {
        Drawable mDrawable = ContextCompat.getDrawable(this, R.drawable.bg_circle_check);
        if (mDrawable != null) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(this.getResources().getColor(color), PorterDuff.Mode.MULTIPLY));
        }
        return mDrawable;
    }
}
