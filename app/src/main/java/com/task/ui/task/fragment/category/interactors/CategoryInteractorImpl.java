package com.task.ui.task.fragment.category.interactors;

import com.task.data.remote.NetworkService;
import com.task.data.remote.oberver.ResponseObserver;
import com.task.model.Category;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CategoryInteractorImpl implements GetCategoryInteractor {

    private static final String TAG = CategoryInteractorImpl.class.getSimpleName();

    @Override
    public void performGetCategory(NetworkService networkService, OnGetCategoryListener onGetCategoryListener) {
        Observable<Response<Category>> observableSingle = networkService.getCategories();
        observableSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<Category>() {
                    @Override
                    public void onResponse(Response<Category> response) {
                        onGetCategoryListener.onSuccess(response.body().getData());
                    }

                    @Override
                    public void onNetworkError(String errorMessage) {
                        onGetCategoryListener.onFailed(errorMessage);
                    }

                    @Override
                    public void onServerError(String errorMessage) {
                        onGetCategoryListener.onFailed(errorMessage);
                    }
                });
    }
}