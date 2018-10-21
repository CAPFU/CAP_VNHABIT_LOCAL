package habit.tracker.habittracker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_CHECK = 0;
    public static final int TYPE_COUNT = 1;
    public static final int TYPE_ADD = 2;
    private Context context;

    private List<MenuItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public MenuRecyclerViewAdapter(Context context, List<MenuItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return  mData == null? 0: mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return TYPE_ADD;
        } else if (TYPE_COUNT == mData.get(position).getType()) {
            return TYPE_COUNT;
        } else if (TYPE_CHECK == mData.get(position).getType()) {
            return TYPE_CHECK;
        }
        return TYPE_COUNT;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_ADD == viewType) {
            View view = mInflater.inflate(R.layout.item_habit_add, parent, false);
            return new ViewHolderAdd(view);
        } else if (TYPE_COUNT == viewType) {
            View view = mInflater.inflate(R.layout.item_habit_count, parent, false);
            return new ViewHolderCount(view);
        } else if (TYPE_CHECK == viewType) {
            View view = mInflater.inflate(R.layout.item_habit_check, parent, false);
            return new ViewHolderCheck(view);
        } else {
            throw new RuntimeException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ADD:
                break;
            case TYPE_COUNT:
                initLayoutCount((ViewHolderCount) holder, mData.get(position));
                break;
            case TYPE_CHECK:
                initLayoutCheck((ViewHolderCheck) holder, mData.get(position));
                break;
        }
    }

    private Drawable getBackground(String color) {
        Drawable mDrawable = ContextCompat.getDrawable(context, R.drawable.bg_shadow);
        if (mDrawable != null && color != null) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(color),PorterDuff.Mode.MULTIPLY));
        }
        return mDrawable;
    }

    private void initLayoutCount(ViewHolderCount holder, MenuItem item) {
        holder.tvCategory.setText(item.getName());
        holder.tvDescription.setText(item.getDescription());
        holder.tvPeriod.setText(item.getPeriod());
        holder.tvNumber.setText(item.getNumber() + " " + item.getUnit());
        holder.tvCount.setText(item.getCount());
        holder.layout.setBackground(getBackground(item.getColor()));
    }

    private void initLayoutCheck(ViewHolderCheck holder, MenuItem item) {
        holder.tvCategory.setText(item.getName());
        holder.tvDescription.setText(item.getDescription());
        holder.tvPeriod.setText(item.getPeriod());
        if (item.getCount().equals("1")) {
            holder.isCheck = true;
            holder.imgCheck.setImageResource(R.drawable.ck_checked);
        } else if (item.getCount().equals("0")) {
            holder.isCheck = false;
            holder.imgCheck.setImageResource(R.drawable.ck_unchecked);
        }
        holder.layout.setBackground(getBackground(item.getColor()));
    }

    public class ViewHolderCount extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout layout;
        TextView tvCategory;
        TextView tvDescription;
        TextView tvPeriod;
        TextView tvNumber;
        TextView tvCount;
        View btnPlus;
        View btnMinus;

        ViewHolderCount(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            layout = itemView.findViewById(R.id.rl_habit);

            tvCategory = itemView.findViewById(R.id.tv_category);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPeriod = itemView.findViewById(R.id.tv_period);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvCount = itemView.findViewById(R.id.tv_count);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnPlus.setOnClickListener(this);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnMinus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_plus) {
//                Toast.makeText(context, "btn_plus", Toast.LENGTH_SHORT).show();
                int num = Integer.parseInt(tvCount.getText().toString());
                num = num + 1;
                tvCount.setText(num + "");
                mClickListener.onAdjustcount(view, TYPE_COUNT, getAdapterPosition(), num);
            } else if (view.getId() == R.id.btn_minus) {
//                Toast.makeText(context, "btn_minus", Toast.LENGTH_SHORT).show();
                int num = Integer.parseInt(tvCount.getText().toString());
                num = num > 0? num - 1: 0;
                tvCount.setText(num + "");
                mClickListener.onAdjustcount(view, TYPE_COUNT, getAdapterPosition(), num);
            } else if (mClickListener != null) {
                mClickListener.onItemClick(view, TYPE_COUNT, getAdapterPosition());
            }
        }
    }

    public class ViewHolderCheck extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout layout;
        TextView tvCategory;
        TextView tvDescription;
        TextView tvPeriod;
        ImageView imgCheck;
        boolean isCheck = false;

        ViewHolderCheck(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            layout = itemView.findViewById(R.id.rl_habit);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPeriod = itemView.findViewById(R.id.tv_period);
            imgCheck = itemView.findViewById(R.id.ck_check);
            imgCheck.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.ck_check) {
                if (isCheck) {
                    isCheck = false;
                    imgCheck.setImageResource(R.drawable.ck_unchecked);
                    mClickListener.onAdjustcount(view, TYPE_CHECK, getAdapterPosition(), 0);
                } else {
                    isCheck = true;
                    imgCheck.setImageResource(R.drawable.ck_checked);
                    mClickListener.onAdjustcount(view, TYPE_CHECK, getAdapterPosition(), 1);
                }
            } else if (mClickListener != null) {
                mClickListener.onItemClick(view, TYPE_CHECK, getAdapterPosition());
            }
        }
    }

    public class ViewHolderAdd extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewHolderAdd(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, TYPE_ADD, getAdapterPosition());
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onAdjustcount(View view, int type, int position, int count);
        void onItemClick(View view, int type, int position);
    }
}
