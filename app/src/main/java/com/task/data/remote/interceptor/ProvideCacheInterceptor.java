package com.task.data.remote.interceptor;

import android.content.Context;
import androidx.annotation.NonNull;
import com.task.data.remote.RemoteConstants;
import com.task.di.qualifier.applicationlevel.ApplicationContext;
import com.task.utils.LogcatUtil;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

/*
 * This interceptor will be called ONLY if the network is available
 *
 * If there is Internet, get the cache that was stored 5 seconds ago.
 * If the cache is older than 5 seconds, then discard it,
 * and indicate an error in fetching the response.
 * The 'max-age' attribute is responsible for this behavior.
 */
public class ProvideCacheInterceptor  implements Interceptor {

    private static final String TAG = ProvideCacheInterceptor.class.getSimpleName();
    private Context context;

    @Inject
    public ProvideCacheInterceptor(@ApplicationContext Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        LogcatUtil.errorMessage(TAG, "network interceptor: called.");

        Response response = chain.proceed(chain.request());

        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(RemoteConstants.MAX_AGE, TimeUnit.SECONDS)
                .build();

        return response.newBuilder()
                .removeHeader(RemoteConstants.HEADER_PRAGMA)
                .removeHeader(RemoteConstants.HEADER_CACHE_CONTROL)
                .header(RemoteConstants.HEADER_CACHE_CONTROL, cacheControl.toString())
                .build();
    }
}
