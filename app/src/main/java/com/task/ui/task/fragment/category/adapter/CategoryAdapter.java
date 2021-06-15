package com.task.ui.task.fragment.category.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.task.R;
import com.task.model.CategoryList;
import com.task.ui.common.adapter.BaseAdapter;
import com.task.ui.common.adapter.BaseViewHolder;
import com.task.ui.common.adapter.RecyclerViewItemClickListener;
import com.task.utils.PicassoImageLoader;

import java.util.List;

public class CategoryAdapter extends BaseAdapter<CategoryList, CategoryAdapter.ItemViewHolder> {

    private Activity activityContext;

    public CategoryAdapter(Activity activityContext, List<CategoryList> recyclerViewItemModelList) {
        super(recyclerViewItemModelList);
        this.activityContext = activityContext;
    }

    @Override
    protected int getItemLayoutResource(int viewType) {
        return R.layout.category_row;
    }

    @Override
    protected ItemViewHolder initViewHolder(View view, int viewType) {
        return new ItemViewHolder(view);
    }

    public class ItemViewHolder extends BaseViewHolder {

        private TextView categoryName;
        private ImageView categoryImage;

        public ItemViewHolder(@NonNull View itemView) {

            super(itemView, new RecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }

        @Override
        public void onBind(Object item) {
            if(item instanceof CategoryList)
            {
                categoryName.setText(((CategoryList) item).getCategoryName());
                PicassoImageLoader.loadImage(activityContext,((CategoryList) item).getImage(),categoryImage);
            }
        }
    }
}