package com.task.ui.task.fragment.item.interactors;

import com.task.data.remote.NetworkService;
import com.task.model.ItemList;
import java.util.List;

public interface GetItemInteractor {

    void performGetItem(String categoryId, NetworkService networkService, OnGetItemListener onGetItemListener);

    interface OnGetItemListener {
        void onSuccess(List<ItemList> itemLists);
        void onFailed(String errorMessage);
    }
}
