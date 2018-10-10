package habit.tracker.habittracker;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HabitActivity extends AppCompatActivity implements View.OnClickListener {

    View btnBack;

    View reminder;

    // group 1
    Button btng1Build;
    Button btng1Quit;
    int g1Sel = 0;

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
        reminder = findViewById(R.id.reminder);
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitActivity.this, EmptyActivity.class);
                HabitActivity.this.startActivity(intent);
            }
        });
        btng1Build = findViewById(R.id.g1_btn_build);
        btng1Build.setOnClickListener(this);
        btng1Quit = findViewById(R.id.g1_btn_quit);
        btng1Quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.g1_btn_build:
                if (g1Sel == 1) {
                    g1Sel = 0;
                    btng1Build.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_green));
                    btng1Quit.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
                }
                break;
            case R.id.g1_btn_quit:
                if (g1Sel == 0) {
                    g1Sel = 1;
                    btng1Build.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
                    btng1Quit.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_button_green));
                }
                break;
        }
    }
}
