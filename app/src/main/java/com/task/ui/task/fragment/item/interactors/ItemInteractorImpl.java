package com.task.ui.task.fragment.item.interactors;

import com.task.data.remote.NetworkService;
import com.task.data.remote.oberver.ResponseObserver;
import com.task.model.Category;
import com.task.model.Item;
import com.task.ui.task.fragment.category.interactors.GetCategoryInteractor;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ItemInteractorImpl implements GetItemInteractor {

    private static final String TAG = ItemInteractorImpl.class.getSimpleName();

    @Override
    public void performGetItem(String categoryId, NetworkService networkService, OnGetItemListener onGetItemListener) {
        Observable<Response<Item>> observableSingle = networkService.getGetItemsUsingCategoryId(categoryId);
        observableSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<Item>() {
                    @Override
                    public void onResponse(Response<Item> response) {
                        onGetItemListener.onSuccess(response.body().getData());
                    }

                    @Override
                    public void onNetworkError(String errorMessage) {
                        onGetItemListener.onFailed(errorMessage);
                    }

                    @Override
                    public void onServerError(String errorMessage) {
                       onGetItemListener.onFailed(errorMessage);
                    }
                });
    }
}