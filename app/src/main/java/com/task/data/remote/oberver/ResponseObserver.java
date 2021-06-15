package com.task.data.remote.oberver;

import com.task.data.remote.errorhandler.ErrorHandler;
import com.task.data.remote.rx.DisposableManager;
import com.task.utils.LogcatUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import retrofit2.Response;
import static java.net.HttpURLConnection.HTTP_OK;

public abstract class ResponseObserver<T> implements Observer<Response<T>> {

    public static final String TAG = "ResponseObserver<T>";

    @Override
    public void onSubscribe(Disposable disposable) {
        DisposableManager.add(disposable);
    }

    @Override
    public void onNext(Response<T> response) {
        if(response != null)
        {
            if (response.code() == HTTP_OK) {
                onResponse(response);
            }
            else
            {
                onServerError(ErrorHandler.getErrorMessage(new HttpException(response)));
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException)
        {
            onServerError(ErrorHandler.getErrorMessage(e));
        }
        else
        {
            onNetworkError(ErrorHandler.getErrorMessage(e));
        }
    }

    @Override
    public void onComplete() {
        LogcatUtil.errorMessage(TAG,"All data emitted.");
    }

    public abstract void onResponse(Response<T>  response);
    public abstract void onNetworkError(String errorMessage);
    public abstract void onServerError(String errorMessage);
}

