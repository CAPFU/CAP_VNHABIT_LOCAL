package habit.tracker.habittracker;

import android.content.Intent;
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

    // group 1
    @BindView(R.id.g1_btn_build)
    Button btng1Build;
    @BindView(R.id.g1_btn_quit)
    Button btng1Quit;
    int buildType = 0;

    // group 2
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

    @BindView(R.id.btn_unit_no)
    Button btnNoUnit;
    @BindView(R.id.btn_unit_set)
    Button btnSetUnit;
    String countUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
        ButterKnife.bind(this);

        btnWatchMode = btnDaily;
    }

    @OnClick({R.id.g1_btn_build, R.id.g1_btn_quit})
    public void setBuildType(View v) {
        switch (v.getId()) {
            case R.id.g1_btn_build:
                setWhiteBg(btng1Quit);
                setGreenBg(btng1Build);
                buildType = 0;
                break;
            case R.id.g1_btn_quit:
                setWhiteBg(btng1Build);
                setGreenBg(btng1Quit);
                break;
        }
    }

    @OnClick({R.id.g2_btn_daily, R.id.g2_btn_weekly, R.id.g2_btn_monthly, R.id.g2_btn_yearly})
    public void setWatchMode(View view) {
        switch (view.getId()) {
            case R.id.g2_btn_daily:
                setWhiteBg(btnWatchMode);
                setGreenBg(btnDaily);
                tvCountUnit.setText("trong một ngày");
                btnWatchMode = btnDaily;
                watchMode = 0;
                break;
            case R.id.g2_btn_weekly:
                setWhiteBg(btnWatchMode);
                setGreenBg(btnWeekly);
                tvCountUnit.setText("trong một tuần");
                btnWatchMode = btnWeekly;
                watchMode = 1;
                break;
            case R.id.g2_btn_monthly:
                setWhiteBg(btnWatchMode);
                setGreenBg(btnMonthly);
                tvCountUnit.setText("trong một tháng");
                btnWatchMode = btnMonthly;
                watchMode = 2;
                break;
            case R.id.g2_btn_yearly:
                setWhiteBg(btnWatchMode);
                setGreenBg(btnYearly);
                tvCountUnit.setText("trong một năm");
                btnWatchMode = btnYearly;
                watchMode = 3;
                break;
        }
    }

    @OnClick({R.id.ck_type_check, R.id.ck_type_count})
    public void checkCountType(View v) {
        if (v.getId() == R.id.ck_type_check) {
            uncheck(imgTypeCount);
            check(imgTypeCheck);
            editCount.setEnabled(false);
            typeCount = 0;
        } else if (v.getId() == R.id.ck_type_count) {
            uncheck(imgTypeCheck);
            check(imgTypeCount);
            editCount.setEnabled(true);
            typeCount = 1;
        }
    }

    @OnClick({R.id.btn_unit_set, R.id.btn_unit_no})
    public void setCountUnit(View v) {

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

    public void showEmpty(View v) {
        Intent intent = new Intent(HabitActivity.this, EmptyActivity.class);
        HabitActivity.this.startActivity(intent);
    }

    public void cancel(View v) {
        finish();
    }
}
