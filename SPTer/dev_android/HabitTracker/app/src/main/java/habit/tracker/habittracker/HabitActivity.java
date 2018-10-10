package habit.tracker.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HabitActivity extends AppCompatActivity {

    View btnBack;

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
    }
}
