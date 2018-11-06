package habit.tracker.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportSummaryActivity extends AppCompatActivity {

    @BindView(R.id.calendar)
    RecyclerView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report_details);
        ButterKnife.bind(this);

        calendar.setLayoutManager(new GridLayoutManager(this, 7));
    }
}
