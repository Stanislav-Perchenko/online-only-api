package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cam.alperez.samples.onlineonlyapi.R;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.ui.common.OnItemClickListener;
import cam.alperez.samples.onlineonlyapi.utils.IntId;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    private final List<CategoryEntity> data;

    private OnItemClickListener<CategoryEntity> itemClickListener;

    private final Set<Integer> withProgressItemIds = new HashSet<>(10);

    public CategoryListAdapter(Context ctx) {
        inflater = LayoutInflater.from(ctx);
        data = new ArrayList<>(10);
    }

    public void setItemClickListener(OnItemClickListener<CategoryEntity> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<CategoryEntity> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        if (data.size() > 0) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemLoadingProgress(IntId<CategoryEntity> categoryId, boolean isProgress) {
        boolean changed;
        if (isProgress) {
            changed = withProgressItemIds.add(categoryId.idValue);
        } else {
            changed = withProgressItemIds.remove(categoryId.idValue);
        }
        if (changed) notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId().idValue;
    }

    public CategoryEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryEntity model = data.get(position);
        holder.bindData(position, model);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private int position;
        private TextView tvTitle;
        private View vProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.category_title);
            vProgress = itemView.findViewById(R.id.item_load_progress);
            itemView.findViewById(R.id.clickable_container).setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(position, getItem(position));
                }
            });
        }

        void bindData(int position, CategoryEntity category) {
            this.position = position;
            tvTitle.setText(category.getDisplayName());
            final boolean isProgress = withProgressItemIds.contains(category.getId().idValue);
            vProgress.setVisibility(isProgress ? VISIBLE : INVISIBLE);
        }
    }
}
