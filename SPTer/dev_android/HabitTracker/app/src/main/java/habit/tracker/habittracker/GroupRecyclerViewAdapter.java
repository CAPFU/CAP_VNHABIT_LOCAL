package habit.tracker.habittracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.GroupViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    String[] data;

    public GroupRecyclerViewAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.group_item, viewGroup, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.tvName.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_itemName);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
