package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cam.alperez.samples.onlineonlyapi.R;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    private final List<CategoryEntity> data;

    public CategoryListAdapter(Context ctx) {
        inflater = LayoutInflater.from(ctx);
        data = new ArrayList<>(10);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<CategoryEntity> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        if (data.size() > 0) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId().idValue;
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


    static class ViewHolder extends RecyclerView.ViewHolder {

        private int position;
        private TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.category_title);
        }

        void bindData(int position, CategoryEntity category) {
            this.position = position;
            tvTitle.setText(category.getDisplayName());
        }
    }
}
