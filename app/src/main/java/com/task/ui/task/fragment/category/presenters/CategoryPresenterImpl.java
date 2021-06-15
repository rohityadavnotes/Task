package com.task.ui.task.fragment.category.presenters;

import com.task.data.remote.NetworkService;
import com.task.model.CategoryList;
import com.task.ui.common.presenters.BasePresenter;
import com.task.ui.task.fragment.category.interactors.CategoryInteractorImpl;
import com.task.ui.task.fragment.category.interactors.GetCategoryInteractor;
import com.task.ui.task.fragment.category.views.CategoryView;
import java.util.List;

public class CategoryPresenterImpl<V extends CategoryView> extends BasePresenter<V> implements CategoryPresenter<V> ,
        GetCategoryInteractor.OnGetCategoryListener {

    private GetCategoryInteractor getCategoryInteractor;

    public CategoryPresenterImpl() {
        this.getCategoryInteractor        = new CategoryInteractorImpl();
    }

    @Override
    public void getCategory(NetworkService networkService) {
        if (getMvpView() != null) {
            getMvpView().showProgressBar();
            getCategoryInteractor.performGetCategory(networkService, this);
        }
    }

    @Override
    public void onSuccess(List<CategoryList> categoryLists) {
        if (getMvpView() != null) {
            getMvpView().onSuccess(categoryLists);
            getMvpView().hideProgressBar();
        }
    }

    @Override
    public void onFailed(String errorMessage) {
        if (getMvpView() != null) {
            getMvpView().onFailed(errorMessage);
            getMvpView().hideProgressBar();
        }
    }
}
