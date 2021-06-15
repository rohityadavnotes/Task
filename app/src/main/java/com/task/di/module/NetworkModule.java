package com.task.di.module;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.task.BuildConfig;
import com.task.data.remote.NetworkService;
import com.task.data.remote.RemoteConstants;
import com.task.data.remote.interceptor.ProvideCacheInterceptor;
import com.task.data.remote.interceptor.ProvideOfflineCacheInterceptor;
import com.task.data.remote.util.Tls12SocketFactory;
import com.task.di.qualifier.applicationlevel.ApplicationContext;
import com.task.di.qualifier.applicationlevel.BackEndBaseUrl;
import com.task.di.qualifier.applicationlevel.CachedOkHttpClient;
import com.task.di.qualifier.applicationlevel.NoCachedOkHttpClient;
import com.task.utils.LogcatUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import static android.util.Log.VERBOSE;

@Module
public class NetworkModule {

    private static final String TAG = NetworkModule.class.getSimpleName();

    @Provides
    @Singleton
    @BackEndBaseUrl
    String provideBackEndBaseUrl() {
        return RemoteConstants.BASE_URL;
    }

    @Provides
    @Singleton
    @Named("Authentication_Token")
    String provideAuthenticationToken() {
        return RemoteConstants.AUTHENTICATION_TOKEN;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(@ApplicationContext Context context) {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), RemoteConstants.OK_HTTP_CACHE_DIR), RemoteConstants.OK_HTTP_CACHE_SIZE);
        } catch (Exception e) {
            LogcatUtil.errorMessage(TAG, "Could not create Cache!", e);
        }
        return cache;
    }

    private OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder builder) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                builder.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                builder.connectionSpecs(specs);
            } catch (Exception exc) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return builder;
    }

    @Provides
    @CachedOkHttpClient
    @Singleton
    OkHttpClient provideCachedOkHttpClient(
            Cache cache,
            ProvideOfflineCacheInterceptor provideOfflineCacheInterceptor,
            ProvideCacheInterceptor provideCacheInterceptor) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.followRedirects(true);
        builder.followSslRedirects(true);
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(RemoteConstants.HTTP_CONNECT_TIMEOUT, TimeUnit.MINUTES);
        builder.writeTimeout(RemoteConstants.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(RemoteConstants.HTTP_READ_TIMEOUT, TimeUnit.SECONDS);

        builder.cache(cache);

        builder.addNetworkInterceptor(provideCacheInterceptor);
        builder.addInterceptor(provideOfflineCacheInterceptor);


        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(new LoggingInterceptor.Builder()
                    .setLevel(Level.BASIC)
                    .log(VERBOSE)
                    .build());
        }

        return enableTls12OnPreLollipop(builder).build();
    }

    @Provides
    @NoCachedOkHttpClient
    @Singleton
    OkHttpClient provideNoCachedOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.followRedirects(true);
        builder.followSslRedirects(true);
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(RemoteConstants.HTTP_CONNECT_TIMEOUT, TimeUnit.MINUTES);
        builder.writeTimeout(RemoteConstants.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(RemoteConstants.HTTP_READ_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(new LoggingInterceptor.Builder()
                    .setLevel(Level.BASIC)
                    .log(VERBOSE)
                    .build());
        }

        return enableTls12OnPreLollipop(builder).build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRxRetrofit(
            @BackEndBaseUrl String backEndBaseUrl,
            GsonConverterFactory gsonConverterFactory,
            RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
            @CachedOkHttpClient OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(backEndBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build();
    }

    @Provides
    @Singleton
    NetworkService provideNetworkService(Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }
}
