package com.task.ui.task.fragment.item.views;

import com.task.model.ItemList;
import com.task.ui.common.views.BaseFragmentView;
import java.util.List;

public  interface ItemView extends BaseFragmentView {
    void onSuccess(List<ItemList> itemLists);
    void onFailed(String errorMessage);
}
