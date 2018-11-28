package habit.tracker.habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import habit.tracker.habittracker.common.util.MySharedPreference;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.lbPersonal)
    TextView lbPersonal;
    @BindView(R.id.tvLogout)
    TextView tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvLogout)
    public void logout(View v) {
        MySharedPreference.saveUser(this, null, null, null);
        Intent intent = getIntent();
        intent.putExtra("logout", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.lbPersonal)
    public void editPersonalInfo(View v) {
        Intent intent = new Intent(this, PersonalActivity.class);
        startActivity(intent);
    }
}
