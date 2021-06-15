package com.task.ui.task.fragment.category.interactors;

import com.task.data.remote.NetworkService;
import com.task.model.CategoryList;
import java.util.List;

public interface GetCategoryInteractor {

    void performGetCategory(NetworkService networkService, OnGetCategoryListener onGetCategoryListener);

    interface OnGetCategoryListener {
        void onSuccess(List<CategoryList> categoryLists);
        void onFailed(String errorMessage);
    }
}
