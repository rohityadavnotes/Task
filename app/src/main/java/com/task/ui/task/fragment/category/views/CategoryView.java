package com.task.ui.task.fragment.category.views;

import com.task.model.CategoryList;
import com.task.ui.common.views.BaseFragmentView;
import java.util.List;

public  interface CategoryView extends BaseFragmentView {
    void onSuccess(List<CategoryList> categoryLists);
    void onFailed(String errorMessage);
}
