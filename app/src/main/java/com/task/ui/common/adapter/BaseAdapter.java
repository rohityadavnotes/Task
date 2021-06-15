package com.task.ui.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    @NonNull
    private List<T> data;

    public BaseAdapter(@NonNull List<T> data) {
        this.data = data;
    }

    /***********************************************************************************************
     *********************************** CRUD Operation Methods ************************************
     **********************************************************************************************/

    public void addSingleItem(T item) {
        data.add(item);
        notifyItemInserted(data.size() - 1);
    }

    public void addSingleItemAtSpecificPosition(T item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public void addArrayList(List<T> items) {
        for (T item : items) {
            data.add(item);
        }
        notifyDataSetChanged();
    }

    public void replaceArrayList(List<T> items) {
        data.clear();
        addArrayList(items);
    }

    public void addArray(T[] items) {
        addArrayList(Arrays.asList(items));
        notifyDataSetChanged();
    }

    public void addOrUpdateSingleItem(T item) {
        int i = data.indexOf(item);
        if (i >= 0) {
            data.set(i, item);
            notifyItemChanged(i);
        } else {
            addSingleItem(item);
        }
    }

    public void addOrUpdateArrayList(List<T> items) {
        for (T item : items) {
            addOrUpdateSingleItem(item);
        }
        notifyDataSetChanged();
    }

    public T getSingleItemUsingPosition(int position) {
        return position >= 0 && position < this.data.size() ? this.data.get(position) : null;
    }

    public void removeSingleItemUsingPosition(int position) {
        if (position >= 0 && position < data.size()) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeSingleItem(T item) {
        int position = data.indexOf(item);
        removeSingleItemUsingPosition(position);
    }

    public void clearAllItem() {
        data.clear();
        notifyDataSetChanged();
    }

    /***********************************************************************************************
     ***************** Implemented methods form RecyclerView.ViewHolder class **********************
     ************** RecyclerView.ViewHolder class extend by BaseViewHolder class*******************/

    @Override
    public int getItemCount() {
        return data.size();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutResource(viewType), parent, false);
        return initViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T item = getSingleItemUsingPosition(position);
        if (item != null) {
            holder.onBind(getSingleItemUsingPosition(position));
        }
    }

    /***********************************************************************************************
     ****************************** Activity abstract methods **************************************
     **********************************************************************************************/

    @LayoutRes
    protected abstract int getItemLayoutResource(int viewType);
    protected abstract VH initViewHolder(View view, int viewType);
}
