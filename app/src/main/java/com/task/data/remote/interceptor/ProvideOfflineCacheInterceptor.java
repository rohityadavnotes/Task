package com.task.data.remote.interceptor;

import android.content.Context;
import androidx.annotation.NonNull;
import com.task.data.remote.RemoteConstants;
import com.task.di.qualifier.applicationlevel.ApplicationContext;
import com.task.internet.InternetConnectionCheckerUtil;
import com.task.utils.LogcatUtil;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*
 * This interceptor will be called both if the network is available and if the network is not available
 *
 * If there is no Internet, get the cache that was stored 7 days ago. If the cache is older than 7 days,
 * then discard it, and indicate an error in fetching the response.
 * The 'max-stale' attribute is responsible for this behavior.
 * The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
 */
public class ProvideOfflineCacheInterceptor  implements Interceptor {

    private static final String TAG = ProvideOfflineCacheInterceptor.class.getSimpleName();
    private Context context;

    @Inject
    public ProvideOfflineCacheInterceptor(@ApplicationContext Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        LogcatUtil.errorMessage(TAG, "offline interceptor: called.");
        Request request = chain.request();

        if (!InternetConnectionCheckerUtil.isConnectedAfter23(context)) {

            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(RemoteConstants.MAX_STALE, TimeUnit.DAYS)
                    .build();

            request = request.newBuilder()
                    .removeHeader(RemoteConstants.HEADER_PRAGMA)
                    .removeHeader(RemoteConstants.HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build();
        }

        return chain.proceed(request);
    }
}

