package habit.tracker.habittracker;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HabitActivity extends AppCompatActivity implements View.OnClickListener {

    View btnBack;

    Button cur1;
    Button cur2;

    // group 1
    Button btng1Build;
    Button btng1Quit;
    int g1Sel = 0;

    // group 2
    Button btng2Daily;
    Button btng2Weekly;
    Button btng2Monthly;
    Button btng2Yearly;
    int g2Sel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HabitActivity.this.finish();
            }
        });

        btng1Build = findViewById(R.id.g1_btn_build);
        btng1Build.setOnClickListener(this);
        btng1Quit = findViewById(R.id.g1_btn_quit);
        btng1Quit.setOnClickListener(this);
        cur1 = btng1Build;

        btng2Daily = findViewById(R.id.g2_btn_daily);
        btng2Daily.setOnClickListener(this);
        btng2Weekly = findViewById(R.id.g2_btn_weekly);
        btng2Weekly.setOnClickListener(this);
        btng2Monthly = findViewById(R.id.g2_btn_monthly);
        btng2Monthly.setOnClickListener(this);
        btng2Yearly = findViewById(R.id.g2_btn_yearly);
        btng2Yearly.setOnClickListener(this);
        cur2 = btng2Daily;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.g1_btn_build:
                setWhiteBg(cur1);
                setGreenBg(btng1Build);
                cur1 = btng1Build;
                g1Sel = 0;
                break;
            case R.id.g1_btn_quit:
                setWhiteBg(cur1);
                setGreenBg(btng1Quit);
                cur1 = btng1Quit;
                g1Sel = 1;
                break;
            case R.id.g2_btn_daily:
                setWhiteBg(cur2);
                setGreenBg(btng2Daily);
                cur2 = btng2Daily;
                g2Sel = 0;
                break;
            case R.id.g2_btn_weekly:
                setWhiteBg(cur2);
                setGreenBg(btng2Weekly);
                cur2 = btng2Weekly;
                g2Sel = 1;
                break;
            case R.id.g2_btn_monthly:
                setWhiteBg(cur2);
                setGreenBg(btng2Monthly);
                cur2 = btng2Monthly;
                g2Sel = 2;
                break;
            case R.id.g2_btn_yearly:
                setWhiteBg(cur2);
                setGreenBg(btng2Yearly);
                cur2 = btng2Yearly;
                g2Sel = 3;
                break;
        }
    }

    public void setGreenBg(View v) {
        v.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_green));
    }

    public void setWhiteBg(View v) {
        v.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
    }

    public void showEmpty(View v) {
        Intent intent = new Intent(HabitActivity.this, EmptyActivity.class);
        HabitActivity.this.startActivity(intent);
    }

    public void cancel(View v) {
        finish();
    }
}
