package habit.tracker.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener, TimePicker.OnTimeChangedListener {

    @BindView(R.id.reminderTime_picker)
    TimePicker timePicker;

    @BindView(R.id.spinner_repeat)
    NumberPicker numberPicker;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.btnOk)
    Button btnOk;

    int hour;
    int minute;
    int repeatTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);

        timePicker.setOnTimeChangedListener(this);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setOnValueChangedListener(this);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        this.repeatTime = newVal;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }

    @OnClick(R.id.btnOk)
    public void remind(Button ok) {

    }

    @OnClick(R.id.btnCancel)
    public void cancel(Button cancel) {

    }
}
