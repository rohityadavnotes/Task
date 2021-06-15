package com.task.data.remote;

import com.task.BuildConfig;

public class RemoteConstants {

    public static final String CONTENT_TYPE             = "Content-Type";
    public static final String ACCEPT                   = "Accept";
    public static final String USER_AGENT               = "User-Agent";
    public static final String AUTHORIZATION            = "Authorization";
    public static final String HEADER_CACHE_CONTROL     = "Cache-Control";
    public static final String HEADER_PRAGMA            = "Pragma";

    /*
     * 5 Second
     * 7 Days ago
     */
    public static final int MAX_AGE                     = 5;
    public static final int MAX_STALE                   = 7;

    /*
     * 60 Second
     * 7 Days ago
     */
    public static final int MAX_AGE_STRING              = 60;
    public static final int MAX_STALE_STRING            = 60 * 60 * 24 * 7;

    public static final String MAX_AGE_CACHE_STRING     = "public, max-age="+MAX_AGE_STRING;
    public static final String MAX_STALE_CACHE_STRING   = "public, only-if-cached, max-stale="+MAX_STALE_STRING;


    public static final String OK_HTTP_CACHE_DIR           = "okhttp_cache";
    /*
     * 10 MB
     */
    public static final long OK_HTTP_CACHE_SIZE            = 10 * 1024 * 1024;

    /*
     * Note :  We can set timeouts settings on the underlying HTTP client.
     * If we don’t specify a client, Retrofit will create one with default connect and read timeouts.
     * By default, Retrofit uses the following timeouts :
     *                                                      Connection timeout: 10 seconds
     *                                                      Read timeout: 10 seconds
     *                                                      Write timeout: 10 seconds
     */
    public static final int HTTP_CONNECT_TIMEOUT             = 1;
    public static final int HTTP_READ_TIMEOUT                = 30;
    public static final int HTTP_WRITE_TIMEOUT               = 15;

    /*
     * Network Base Config
     */
    public static final String HOST_URL                 = BuildConfig.HOST_URL;
    public static final String BASE_URL                 = "https://backend24.000webhostapp.com/";
    public static final String API_ACCESS_KEY           = BuildConfig.API_ACCESS_KEY;
    public static final String AUTHENTICATION_TOKEN     = BuildConfig.API_ACCESS_KEY;

    /*
     * End points
     */
    public static final String ADD_ITEM               = "set_items.php";
    public static final String CATEGORY               = "get_categories.php";
    public static final String ITEM                   = "get_items_using_category_id.php";

    /*
     * Response Status
     */
    public static final int SUCCESS_CODE = 0;
}
