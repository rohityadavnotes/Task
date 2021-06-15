package com.task.ui.common.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    protected RecyclerViewItemClickListener recyclerViewItemClickListener;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(View itemView, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        super(itemView);
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public abstract void onBind(T item);

    @Override
    public void onClick(View v) {
        if (recyclerViewItemClickListener != null) {
            recyclerViewItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (recyclerViewItemClickListener != null) {
            recyclerViewItemClickListener.onItemLongClick(v, getAdapterPosition());
            return true;
        }
        return false;
    }
}
