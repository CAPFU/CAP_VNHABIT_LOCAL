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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HabitActivity extends AppCompatActivity {

    @BindView(R.id.g1_btn_build)
    Button btnHabitBuild;
    @BindView(R.id.g1_btn_quit)
    Button btnHabitQuit;
    int buildType = 0;

    Button btnWatchMode;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
        ButterKnife.bind(this);

        btnWatchMode = btnDaily;
        color1.setBackground(getCircleBackground(R.color.color1));
        color2.setBackground(getCircleBackground(R.color.color2));
        color3.setBackground(getCircleBackground(R.color.color3));
        color4.setBackground(getCircleBackground(R.color.color4));
        color5.setBackground(getCircleBackground(R.color.color5));
        color6.setBackground(getCircleBackground(R.color.color6));
        color7.setBackground(getCircleBackground(R.color.color7));
        color8.setBackground(getCircleBackground(R.color.color8));
        color9.setBackground(getCircleBackground(R.color.color9));
        color10.setBackground(getCircleBackground(R.color.color10));
    }

    @OnClick({R.id.g1_btn_build, R.id.g1_btn_quit})
    public void setBuildType(View v) {
        setGreenBg(v);
        switch (v.getId()) {
            case R.id.g1_btn_build:
                setWhiteBg(btnHabitQuit);
                buildType = 0;
                break;
            case R.id.g1_btn_quit:
                setWhiteBg(btnHabitBuild);
                buildType = 1;
                break;
        }
    }

    @OnClick({R.id.g2_btn_daily, R.id.g2_btn_weekly, R.id.g2_btn_monthly, R.id.g2_btn_yearly})
    public void setWatchMode(View view) {
        setWhiteBg(btnWatchMode);
        setGreenBg(view);
        switch (view.getId()) {
            case R.id.g2_btn_daily:
                tvCountUnit.setText("trong một ngày");
                btnWatchMode = btnDaily;
                watchMode = 0;
                break;
            case R.id.g2_btn_weekly:
                tvCountUnit.setText("trong một tuần");
                btnWatchMode = btnWeekly;
                watchMode = 1;
                break;
            case R.id.g2_btn_monthly:
                tvCountUnit.setText("trong một tháng");
                btnWatchMode = btnMonthly;
                watchMode = 2;
                break;
            case R.id.g2_btn_yearly:
                tvCountUnit.setText("trong một năm");
                btnWatchMode = btnYearly;
                watchMode = 3;
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
}
