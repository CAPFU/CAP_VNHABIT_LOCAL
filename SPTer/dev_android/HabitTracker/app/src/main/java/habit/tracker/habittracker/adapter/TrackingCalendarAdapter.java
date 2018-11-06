package habit.tracker.habittracker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import habit.tracker.habittracker.R;

public class TrackingCalendarAdapter extends RecyclerView.Adapter<TrackingCalendarAdapter.CalendarNumberViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    List<CalendarNumber> data;

    public TrackingCalendarAdapter(Context context, List<CalendarNumber> data) {
        this.context = context;
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CalendarNumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_calendar, viewGroup, false);
        return new CalendarNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarNumberViewHolder holder, int pos) {
        if (data.get(pos).isSelected()) {
            holder.tvNumber.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_circle_fill));
        } else {
            holder.tvNumber.setBackground(ContextCompat.getDrawable(context, android.R.color.transparent));
        }

        holder.tvNumber.setText( data.get(pos).getText() );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected class CalendarNumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNumber;

        public CalendarNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvNumber.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}