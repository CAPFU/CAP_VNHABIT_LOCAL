package habit.tracker.habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupActivity extends AppCompatActivity implements GroupRecyclerViewAdapter.ItemClickListener {
    public static final String GROUP_ITME = "group_itme";
    RecyclerView rvGroupItem;
    GroupRecyclerViewAdapter recyclerViewAdapter;
    String[] data = new String[]{};

    @BindView(R.id.btn_back)
    View btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);

        rvGroupItem = findViewById(R.id.rv_group);
        rvGroupItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new GroupRecyclerViewAdapter(this, data);
        recyclerViewAdapter.setClickListener(this);
        rvGroupItem.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = getIntent();
        intent.putExtra(GROUP_ITME, position);
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.btn_back)
    public void back(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
