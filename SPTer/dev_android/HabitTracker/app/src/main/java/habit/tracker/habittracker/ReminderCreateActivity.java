package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import habit.tracker.habittracker.common.util.DateGenerator;

public class ReminderCreateActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    public static final String REMIND_TEXT = "remind_text";
    public static final String REMIND_HOUR = "remind_hour";
    public static final String REMIND_MINUTE = "remind_minute";
    public static final String REMIND_DATE = "remind_date";
    public static final String REMIND_TYPE = "remind_type";

    @BindView(R.id.edRemindText)
    EditText edRemindText;
    @BindView(R.id.picker_hour)
    NumberPicker pickerHour;
    @BindView(R.id.picker_minute)
    NumberPicker pickerMinute;
    @BindView(R.id.picker_date)
    NumberPicker pickerDate;
    @BindView(R.id.btn_save)
    View btnSave;
    @BindView(R.id.btn_cancel)
    View btnCancel;

    @BindView(R.id.btn_TypeAll)
    View btnTypeAll;
    @BindView(R.id.btn_TypeDaily)
    View btnTypeDaily;
    @BindView(R.id.btn_TypeWeekly)
    View btnTypeWeek;
    @BindView(R.id.btn_TypeMonthly)
    View btnTypeMonth;
    @BindView(R.id.btn_TypeYearly)
    View btnTypeYear;
    View vType;

    int[] hours;
    int[] minutes;
    String[] dates;

    int hour;
    int minute;
    int type;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reminder_create);
        ButterKnife.bind(this);

        hour = 0;
        minute = 0;
        type = -1;
        vType = btnTypeAll;

        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int date = ca.get(Calendar.DATE);
        dates = DateGenerator.getDatesInMonth(year, month, date, true);
        String[] displayDates = convertDisplayDate(dates);
        pickerDate.setMinValue(0);
        pickerDate.setMaxValue(displayDates.length - 1);
        pickerDate.setDisplayedValues(displayDates);
        pickerDate.setOnValueChangedListener(this);

        this.date = dates[0];

        hours = new int[24];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = i;
        }
        pickerHour.setMinValue(0);
        pickerHour.setMaxValue(hours.length - 1);
        pickerHour.setDisplayedValues(getStringArr(hours));
        pickerHour.setOnValueChangedListener(this);

        minutes = new int[59];
        for (int i = 0; i < minutes.length; i++) {
            minutes[i] = i;
        }
        pickerMinute.setMinValue(0);
        pickerMinute.setMaxValue(minutes.length - 1);
        pickerMinute.setDisplayedValues(getStringArr(minutes));
        pickerMinute.setOnValueChangedListener(this);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()){
            case R.id.picker_hour:
                hour = hours[newVal];
                break;
            case R.id.picker_minute:
                minute = minutes[newVal];
                break;
            case R.id.picker_date:
                date = dates[newVal];
                break;
        }
    }

    @OnClick(R.id.btn_cancel)
    public void cancel(View v) {
        finish();
    }

    @OnClick(R.id.btn_save)
    public void save(View v) {
        if (TextUtils.isEmpty(edRemindText.getText().toString().trim())) {
            Toast.makeText(this, "Chưa thêm nội dung nhắt nhở.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();
        intent.putExtra(REMIND_TEXT, edRemindText.getText().toString());
        intent.putExtra(REMIND_HOUR, hour);
        intent.putExtra(REMIND_MINUTE, minute);
        intent.putExtra(REMIND_DATE, date);
        intent.putExtra(REMIND_TYPE, type);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick({R.id.btn_TypeAll, R.id.btn_TypeDaily, R.id.btn_TypeWeekly, R.id.btn_TypeMonthly, R.id.btn_TypeYearly})
    public void setHabitType(View view) {
        setWhiteBg(vType);
        setGreenBg(view);
        vType = view;
        type = Integer.parseInt(view.getTag().toString());
    }

    private String[] convertDisplayDate(String[] dates) {
        String[] res = new String[dates.length];
        int year;
        int month;
        int day;
        String[] arr;
        String str;
        Calendar ca = Calendar.getInstance();
        for (int i=0; i < dates.length; i++) {
            arr = dates[i].split("-");
            year = Integer.parseInt(arr[0]);
            month = Integer.parseInt(arr[1]);
            day = Integer.parseInt(arr[2]);
            ca.set(year, month - 1, day);
            str = "";
            switch(ca.get(Calendar.DAY_OF_WEEK)){
                case Calendar.MONDAY:
                    str = "Th 2";
                    break;
                case Calendar.TUESDAY:
                    str = "Th 3";
                    break;
                case Calendar.WEDNESDAY:
                    str = "Th 4";
                    break;
                case Calendar.THURSDAY:
                    str = "Th 5";
                    break;
                case Calendar.FRIDAY:
                    str = "Th 6";
                    break;
                case Calendar.SATURDAY:
                    str = "Th 7";
                    break;
                case Calendar.SUNDAY:
                    str = "CN";
                    break;
            }
            str += " "+ day + " Th " + month;
            res[i] = str;
        }
        return res;
    }

    private String[] getStringArr(int[] arr) {
        String[] res = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = String.valueOf(arr[i]);
        }
        return res;
    }

    public void setGreenBg(View v) {
        v.setBackground(ContextCompat.getDrawable(this, R.drawable.button_green));
    }

    public void setWhiteBg(View v) {
        v.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
    }
}