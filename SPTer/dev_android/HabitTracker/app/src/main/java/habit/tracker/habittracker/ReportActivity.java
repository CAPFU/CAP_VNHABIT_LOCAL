package habit.tracker.habittracker;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import habit.tracker.habittracker.common.Generator;
import habit.tracker.habittracker.common.chart.DayAxisValueFormatter;
import habit.tracker.habittracker.common.chart.MyAxisValueFormatter;
import habit.tracker.habittracker.repository.Database;
import habit.tracker.habittracker.repository.habit.DateTracking;


public class ReportActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    @BindView(R.id.chart)
    BarChart chart;
    @BindView(R.id.pre)
    View pre;
    @BindView(R.id.next)
    View next;
    @BindView(R.id.displayTime)
    TextView time;
    @BindView(R.id.total)
    TextView tvTotal;
    @BindView(R.id.totalDone)
    TextView tvTotalDone;

    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        initChart();
    }

    @OnClick(R.id.pre)
    public void pre(View v) {
        currentTime = Generator.getDayPreWeek(currentTime);
        ArrayList<BarEntry> values = loadData(currentTime);
        setData(values);
        chart.invalidate();
    }

    @OnClick(R.id.next)
    public void next(View v) {
        currentTime = Generator.getDayNextWeek(currentTime);
        ArrayList<BarEntry> values = loadData(currentTime);
        setData(values);
        chart.invalidate();
    }

    private void initChart() {
        chart.setOnChartValueSelectedListener(this);
        chart.getDescription().setEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(false);
        chart.getDescription().setEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        // if more than 7 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(7);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter();
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(7, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int date = ca.get(Calendar.DATE);
        currentTime = year + "-" + month + "-" + date;
//        String[] week = Generator.getWeek(year, month, date);
//        String startDate = convert(week[0], "-", "/");
//        String endDate = convert(week[6], "-", "/");
//        time.setText(startDate + " - " + endDate);

        ArrayList<BarEntry> values = loadData(currentTime);
        setData(values);
    }

    private void setData(ArrayList<BarEntry> values) {
        BarDataSet set1;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.animateY(500);

        } else {
            set1 = new BarDataSet(values, "");
            set1.setDrawIcons(false);
            int startColor1 = ContextCompat.getColor(this, R.color.red1);
            int endColor1 = ContextCompat.getColor(this, R.color.red2);

            List<GradientColor> gradientColors = new ArrayList<>();
            gradientColors.add(new com.github.mikephil.charting.model.GradientColor(startColor1, endColor1));

            set1.setGradientColors(gradientColors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            chart.setData(data);
            chart.animateY(500);
        }
    }

    private  ArrayList<BarEntry> loadData(String currentTime) {
        ArrayList<BarEntry> values = new ArrayList<>();
        String[] strs = currentTime.split("-");
        int year = Integer.parseInt(strs[0]);
        int month = Integer.parseInt(strs[1]);
        int date = Integer.parseInt(strs[2]);
        String[] week = Generator.getWeek(year, month, date);

        String startDate = convert(week[0], "-", "/");
        String endDate = convert(week[6], "-", "/");
        time.setText(startDate + " - " + endDate);

        Database db = new Database(this);
        db.open();
        // get all completed habits in one week
        List<DateTracking> total = Database.sHabitDaoImpl.getHabitsBetween(week[0], week[6]);
        List<DateTracking> completedList = new ArrayList<>();
        for (DateTracking item : total) {
            if (item.getHabitEntity().getMonitorNumber() != null
                    && item.getTrackingEntity().getCount() != null
                    && item.getHabitEntity().getMonitorNumber().equals(item.getTrackingEntity().getCount())) {
                completedList.add(item);
            }
        }
        int[] count = countPerDay(week, completedList);
        for (int i = 0; i < 7; i++) {
            values.add(new BarEntry(i, count[i]));
        }
        db.close();

        tvTotal.setText(String.valueOf(total.size()));
        tvTotalDone.setText(String.valueOf(completedList.size()));

        return values;
    }

    private int[] countPerDay(String[] week, List<DateTracking> list) {
        int[] count = new int[7];
        for (int i = 0; i < list.size(); i++) {
            String date = list.get(i).getTrackingEntity().getCurrentDate();
            if (date.equals(week[0])) {
                ++count[0];
            } else if (date.equals(week[1])) {
                ++count[1];
            } else if (date.equals(week[2])) {
                ++count[2];
            } else if (date.equals(week[3])) {
                ++count[3];
            } else if (date.equals(week[4])) {
                ++count[4];
            } else if (date.equals(week[5])) {
                ++count[5];
            } else if (date.equals(week[6])) {
                ++count[6];
            }
        }
        return count;
    }

    private String convert(String date, String p1, String p2) {
        String[] strs = date.split(p1);
        return strs[2] + p2 + strs[1] + strs[0];
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
