package com.task.ui.task.fragment.item.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.task.R;
import com.task.data.local.sqlite.Cart;
import com.task.data.local.sqlite.CartHelper;
import com.task.model.ItemList;
import com.task.ui.common.adapter.BaseAdapter;
import com.task.ui.common.adapter.BaseViewHolder;
import com.task.ui.task.TaskActivity;
import com.task.utils.LogcatUtil;
import com.task.utils.PicassoImageLoader;
import com.task.utils.ToastUtil;

import java.util.List;

public class ItemAdapter extends BaseAdapter<ItemList, ItemAdapter.ItemViewHolder> {

    private Activity activityContext;
    private CartHelper cartHelper;

    public ItemAdapter(Activity activityContext, List<ItemList> recyclerViewItemModelList) {
        super(recyclerViewItemModelList);
        this.activityContext = activityContext;
        cartHelper = new CartHelper(activityContext);
    }

    @Override
    protected int getItemLayoutResource(int viewType) {
        return R.layout.item_row;
    }

    @Override
    protected ItemViewHolder initViewHolder(View view, int viewType) {
        return new ItemViewHolder(view);
    }

    public class ItemViewHolder extends BaseViewHolder {

        private TextView itemName;
        private TextView itemPrice;
        private ImageView itemImage;
        private Button addToCart;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);
            addToCart = itemView.findViewById(R.id.add_to_cart_button);
        }

        @Override
        public void onBind(Object item) {
            if(item instanceof ItemList)
            {
                itemName.setText(((ItemList) item).getName());
                itemPrice.setText(((ItemList) item).getPrice()+" \u20B9");
                PicassoImageLoader.loadImage(activityContext,((ItemList) item).getImage(),itemImage);

                addToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(cartHelper.checkProductIdExist(((ItemList) item).getId().toString()))
                        {
                            LogcatUtil.debuggingMessage("Add To Cart","Item Already Added");
                        }
                        else
                        {
                            Cart foodCard = new Cart(
                                    ((ItemList) item).getCategoryId().toString(),
                                    ((ItemList) item).getName(),
                                    ((ItemList) item).getId().toString(),
                                    ((ItemList) item).getName(),
                                    ((ItemList) item).getPrice(),
                                    ((ItemList) item).getImage(),
                                    "1",
                                    ((ItemList) item).getPrice()
                            );
                            cartHelper.insertOperationIntoFirstTable(foodCard);
                        }

                        ((TaskActivity) activityContext).updateProductBadge(cartHelper.numberOfRows());
                    }
                });
            }
        }
    }
}