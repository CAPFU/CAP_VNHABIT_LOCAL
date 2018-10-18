package habit.tracker.habittracker;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import habit.tracker.habittracker.api.model.habit.Habit;
import habit.tracker.habittracker.common.Validator;
import habit.tracker.habittracker.common.ValidatorType;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.habit.HabitEntity;

public class HabitActivity extends AppCompatActivity {
    public static final String TYPE_0 = "0";
    public static final String TYPE_1 = "1";
    public static final String TYPE_2 = "2";
    public static final String TYPE_3 = "3";
    public static final String TYPE_4 = "4";
    public static final String TYPE_5 = "5";
    public static final String TYPE_6 = "6";
    public static final String TYPE_7 = "7";
    public static final String TYPE_8 = "8";
    public static final String TYPE_9 = "9";

    @BindView(R.id.edit_habitName)
    EditText editHabitName;

    @BindView(R.id.btn_TargetBuild)
    Button btnHabitBuild;
    @BindView(R.id.btn_TargetQuit)
    Button btnHabitQuit;
    int habitTarget = 0;

    View btnHabitType;
    @BindView(R.id.btn_TypeDaily)
    Button btnDaily;
    @BindView(R.id.btn_TypeWeekly)
    Button btnWeekly;
    @BindView(R.id.btn_TypeMonthly)
    Button btnMonthly;
    @BindView(R.id.btn_TypeYearly)
    Button btnYearly;
    int habitType = 0;

    @BindView(R.id.tv_count_unit)
    TextView tvCountUnit;
    @BindView(R.id.ll_checkDone)
    View chkMonitorCheck;
    @BindView(R.id.ll_checkCount)
    View chkMonitorCount;
    @BindView(R.id.img_checkDone)
    ImageView imgTypeCheck;
    @BindView(R.id.img_checkCount)
    ImageView imgTypeCount;
    @BindView(R.id.edit_checkNumber)
    EditText editCheckNumber;
    @BindView(R.id.edit_monitorUnit)
    EditText editMonitorUnit;
    int monitorType = 0;

    @BindView(R.id.ll_group)
    View selGroup;

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
    boolean[] monitorDate = new boolean[7];

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
    String habitColorCode;
    int[] colors = new int[]{
            R.color.color0,
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9
    };
    List<String> colorsList;

    @BindView(R.id.ll_start_date)
    View mStartDate;
    @BindView(R.id.ll_end_date)
    View mEndDate;
    @BindView(R.id.check_startDate)
    ImageView chkStartDate;
    @BindView(R.id.check_endDate)
    ImageView chkEndDate;
    @BindView(R.id.edit_startDate)
    TextView tvStartDate;
    @BindView(R.id.edit_endDate)
    TextView tvEndDate;
    boolean[] startOrEndDate = new boolean[2];

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.edit_description)
    EditText editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
        ButterKnife.bind(this);

        colorsList = new ArrayList<>();
        for (int colorId : colors) {
            colorsList.add(getResources().getString(colorId));
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // load habit from local data
            initFromSavedHabit();
        } else {
            // init habit type: daily
            btnHabitType = btnDaily;

            // init habit color
            color1.setBackground(getCircleBackground(colorsList.get(0)));
            color2.setBackground(getCircleBackground(colorsList.get(1)));
            color3.setBackground(getCircleBackground(colorsList.get(2)));
            color4.setBackground(getCircleBackground(colorsList.get(3)));
            color5.setBackground(getCircleBackground(colorsList.get(4)));
            color6.setBackground(getCircleBackground(colorsList.get(5)));
            color7.setBackground(getCircleBackground(colorsList.get(6)));
            color8.setBackground(getCircleBackground(colorsList.get(7)));
            color9.setBackground(getCircleBackground(colorsList.get(8)));
            color10.setBackground(getCircleBackground(colorsList.get(9)));
            setHabitColor(color1);

            // init monitor date
            setMonitorDate(btnMon);
            setMonitorDate(btnTue);
            setMonitorDate(btnWed);
            setMonitorDate(btnThu);
            setMonitorDate(btnFri);
            setMonitorDate(btnSat);
            setMonitorDate(btnSun);
        }
    }

    private void initFromSavedHabit() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String habitId = extras.getString(MainActivity.HABIT_ID, null);
            if (habitId != null) {
                Database db = new Database(this);
                db.open();
                HabitEntity habitEntity = Database.sHabitDaoImpl.getHabit(habitId);
                if (habitEntity != null) {
                    // habit name
                    editHabitName.setText(habitEntity.getHabitName());
                    // habit target
                    switch (habitEntity.getHabitTarget()) {
                        case TYPE_0:
                            setHabitTarget(btnHabitBuild);
                            break;
                        case TYPE_1:
                            setHabitTarget(btnHabitQuit);
                            break;
                    }
                    // monitor date
                    if (habitEntity.getMon() != null && habitEntity.getMon().equals(TYPE_1)) {
                        setMonitorDate(btnMon);
                    }
                    if (habitEntity.getTue() != null && habitEntity.getTue().equals(TYPE_1)){
                        setMonitorDate(btnTue);
                    }
                    if (habitEntity.getWed() != null && habitEntity.getWed().equals(TYPE_1)){
                        setMonitorDate(btnWed);
                    }
                    if (habitEntity.getThu() != null && habitEntity.getThu().equals(TYPE_1)){
                        setMonitorDate(btnThu);
                    }
                    if (habitEntity.getFri() != null && habitEntity.getFri().equals(TYPE_1)){
                        setMonitorDate(btnFri);
                    }
                    if (habitEntity.getSat() != null && habitEntity.getSat().equals(TYPE_1)){
                        setMonitorDate(btnSat);
                    }
                    if (habitEntity.getSat() != null && habitEntity.getSun().equals(TYPE_1)) {
                        setMonitorDate(btnSun);
                    }
                    // plan date
                    if (habitEntity.getStartDate() != null) {
                        setStartEndDate(mStartDate);
                        tvStartDate.setText(habitEntity.getStartDate());
                    }
                    if (habitEntity.getEndDate() != null) {
                        setStartEndDate(mEndDate);
                        tvEndDate.setText(habitEntity.getEndDate());
                    }
                    // habit type
                    switch (habitEntity.getHabitType()) {
                        case TYPE_0:
                            setHabitType(btnDaily);
                            break;
                        case TYPE_1:
                            setHabitType(btnWeekly);
                            break;
                        case TYPE_2:
                            setHabitType(btnMonthly);
                            break;
                        case TYPE_3:
                            setHabitType(btnYearly);
                            break;
                    }
                    switch (habitEntity.getMonitorType()) {
                        case TYPE_0:
                            selectMonitorType(chkMonitorCheck);
                            break;
                        case TYPE_1:
                            selectMonitorType(chkMonitorCount);
                            break;
                    }
                    // habit monitor type
                    switch (habitEntity.getMonitorType()) {
                        case TYPE_0:
                            selectMonitorType(chkMonitorCheck);
                            break;
                        case TYPE_1:
                            selectMonitorType(chkMonitorCount);
                            break;
                    }
                    editCheckNumber.setText(habitEntity.getMonitorNumber());
                    editMonitorUnit.setText(habitEntity.getMonitorUnit());
                    // habit color
                    for (int i=0; i < colorsList.size(); i++) {
                        String code = colorsList.get(i);
                        // TODO: optimize this
                        if (habitEntity.getHabitColor().equals(code)) {
                            switch (String.valueOf(i)) {
                                case TYPE_0:
                                    setHabitColor(color1);
                                    break;
                                case TYPE_1:
                                    setHabitColor(color2);
                                    break;
                                case TYPE_2:
                                    setHabitColor(color3);
                                    break;
                                case TYPE_3:
                                    setHabitColor(color4);
                                    break;
                                case TYPE_4:
                                    setHabitColor(color5);
                                    break;
                                case TYPE_5:
                                    setHabitColor(color6);
                                    break;
                                case TYPE_6:
                                    setHabitColor(color7);
                                    break;
                                case TYPE_7:
                                    setHabitColor(color8);
                                    break;
                                case TYPE_8:
                                    setHabitColor(color9);
                                    break;
                                case TYPE_9:
                                    setHabitColor(color10);
                                    break;
                            }
                        }
                    }
                    // habit description
                    editDescription.setText(habitEntity.getHabitDescription());
                }
                db.close();
            }
        }
    }

    @OnClick(R.id.btn_save)
    public void saveHabit(View v) {
        Validator validator = new Validator();
        validator.setErrorMsgListener(new Validator.ErrorMsg() {
            @Override
            public void showError(ValidatorType type, String key) {
                switch (type) {
                    case EMPTY:
                        Toast.makeText(HabitActivity.this, key + " không được trống", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        String userId = MySharedPreference.getUserId(this);
        String habitName = editHabitName.getText().toString();
        String monitorNumber = this.editCheckNumber.getText().toString();
        if (!validator.checkEmpty("Tên thói quen", habitName)) {
            return;
        }
        String monitorUnit = null;
        if (this.monitorType == 1) {
            monitorUnit = this.editMonitorUnit.getText().toString();
            if (!validator.checkEmpty("Đơn vị", habitName)) {
                return;
            }
        }
        if (!validator.checkNumber(monitorNumber, 1)) {
            Toast.makeText(HabitActivity.this, "Số lần phải lớn hon 0", Toast.LENGTH_SHORT).show();
        }
        Habit habit = new Habit();
        habit.setUserId(userId);
        habit.setGroupId(null);
        habit.setHabitName(habitName);
        habit.setHabitTarget(String.valueOf(this.habitTarget));
        habit.setHabitType(String.valueOf(this.habitType));
        habit.setMonitorType(String.valueOf(this.monitorType));
        habit.setMonitorUnit(monitorUnit);
        habit.setMonitorNumber(monitorNumber);
        habit.setStartDate(null);
        habit.setEndDate(null);
        habit.setCreatedDate(null);
        habit.setHabitColor(this.habitColorCode);
        habit.setHabitDescription(this.editDescription.getText().toString());

//        ApiService mService = VnHabitApiUtils.getApiService();
//        mService.addHabit(habit).enqueue(new Callback<HabitResult>() {
//            @Override
//            public void onResponse(Call<HabitResult> call, Response<HabitResult> response) {
//                if (response.body().getResult().equals("1")) {
//                    Toast.makeText(HabitActivity.this, "Tạo thói quen thành công", Toast.LENGTH_LONG).show();
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<HabitResult> call, Throwable t) {
//                Toast.makeText(HabitActivity.this, "Đã xãy ra lỗi", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @OnClick({R.id.btn_TargetBuild, R.id.btn_TargetQuit})
    public void setHabitTarget(View v) {
        setGreenBg(v);
        switch (v.getId()) {
            case R.id.btn_TargetBuild:
                setWhiteBg(btnHabitQuit);
                habitTarget = 0;
                break;
            case R.id.btn_TargetQuit:
                setWhiteBg(btnHabitBuild);
                habitTarget = 1;
                break;
        }
    }

    @OnClick({R.id.btn_TypeDaily, R.id.btn_TypeWeekly, R.id.btn_TypeMonthly, R.id.btn_TypeYearly})
    public void setHabitType(View view) {
        setWhiteBg(btnHabitType);
        setGreenBg(view);
        btnHabitType = view;
        habitType = Integer.parseInt(view.getTag().toString());
        switch (view.getId()) {
            case R.id.btn_TypeDaily:
                tvCountUnit.setText("trong một ngày");
                break;
            case R.id.btn_TypeWeekly:
                tvCountUnit.setText("trong một tuần");
                break;
            case R.id.btn_TypeMonthly:
                tvCountUnit.setText("trong một tháng");
                break;
            case R.id.btn_TypeYearly:
                tvCountUnit.setText("trong một năm");
                break;
        }
    }

    @OnClick({R.id.ll_checkDone, R.id.ll_checkCount})
    public void selectMonitorType(View view) {
        if (view.getId() == R.id.ll_checkDone) {
            uncheck(imgTypeCount);
            check(imgTypeCheck);
            editCheckNumber.setEnabled(false);
            editMonitorUnit.setEnabled(false);
            monitorType = 0;
        } else if (view.getId() == R.id.ll_checkCount) {
            uncheck(imgTypeCheck);
            check(imgTypeCount);
            editCheckNumber.setEnabled(true);
            editMonitorUnit.setEnabled(true);
            monitorType = 1;
        }
    }

    @OnClick(R.id.ll_group)
    public void selectGroup(View view) {
        Intent intent = new Intent(this, GroupActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.btnMon, R.id.btnTue, R.id.btnWed, R.id.btnThu, R.id.btnFri, R.id.btnSat, R.id.btnSun})
    public void setMonitorDate(View v) {
        int tag = Integer.parseInt(v.getTag().toString());
        if (monitorDate[tag]) {
            uncheckDate(v);
        } else {
            checkDate(v);
        }
        monitorDate[tag] = !monitorDate[tag];
    }

    @OnClick({R.id.ll_start_date, R.id.ll_end_date})
    public void setStartEndDate(View v) {
        if (v.getId() == R.id.ll_start_date) {
            if (startOrEndDate[0]) {
                uncheck(chkStartDate);
            } else {
                check(chkStartDate);
            }
            startOrEndDate[0] = !startOrEndDate[0];
        } else if (v.getId() == R.id.ll_end_date) {
            if (startOrEndDate[1]) {
                uncheck(chkEndDate);
            } else {
                check(chkEndDate);
            }
            startOrEndDate[1] = !startOrEndDate[1];
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
        int idx = 0;
        if (habitColor != null) {
            idx = Integer.parseInt(habitColor.getTag().toString());
            unpickColor(habitColor, colorsList.get(idx));
        }
        idx = Integer.parseInt(v.getTag().toString());
        pickColor(v, colors[idx]);
        habitColor = v;
        habitColorCode = getResources().getString(colors[idx]);
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

    public void unpickColor(View v, String colorCode){
        v.setBackground(getCircleBackground(colorCode));
    }

    public void showEmpty(View v) {
        Intent intent = new Intent(HabitActivity.this, EmptyActivity.class);
        HabitActivity.this.startActivity(intent);
    }

    public void cancel(View v) {
        finish();
    }

    private Drawable getCircleBackground(String colorCode) {
        Drawable mDrawable = ContextCompat.getDrawable(this, R.drawable.bg_circle);
        if (mDrawable != null) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(colorCode), PorterDuff.Mode.MULTIPLY));
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
