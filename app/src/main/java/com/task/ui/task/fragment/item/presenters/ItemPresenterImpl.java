package com.task.ui.task.fragment.item.presenters;

import com.task.data.remote.NetworkService;
import com.task.model.CategoryList;
import com.task.model.ItemList;
import com.task.ui.common.presenters.BasePresenter;
import com.task.ui.task.fragment.category.interactors.CategoryInteractorImpl;
import com.task.ui.task.fragment.category.interactors.GetCategoryInteractor;
import com.task.ui.task.fragment.category.presenters.CategoryPresenter;
import com.task.ui.task.fragment.category.views.CategoryView;
import com.task.ui.task.fragment.item.interactors.GetItemInteractor;
import com.task.ui.task.fragment.item.interactors.ItemInteractorImpl;
import com.task.ui.task.fragment.item.views.ItemView;

import java.util.List;

public class ItemPresenterImpl<V extends ItemView> extends BasePresenter<V> implements ItemPresenter<V> ,
        GetItemInteractor.OnGetItemListener {

    private GetItemInteractor getCategoryInteractor;

    public ItemPresenterImpl() {
        this.getCategoryInteractor        = new ItemInteractorImpl();
    }

    @Override
    public void getItem(String categoryId, NetworkService networkService) {
        if (getMvpView() != null) {
            getMvpView().showProgressBar();
            getCategoryInteractor.performGetItem(categoryId, networkService, this);
        }
    }

    @Override
    public void onSuccess(List<ItemList> itemLists) {
        if (getMvpView() != null) {
            getMvpView().onSuccess(itemLists);
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
