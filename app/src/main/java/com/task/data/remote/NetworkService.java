package com.task.data.remote;

import com.google.gson.JsonObject;
import com.task.model.AddItem;
import com.task.model.Category;
import com.task.model.Item;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NetworkService {

    @GET(RemoteConstants.CATEGORY)
    Observable<Response<Category>> getCategories();

    @FormUrlEncoded
    @POST(RemoteConstants.ITEM)
    Observable<Response<Item>> getGetItemsUsingCategoryId(@Field("category_id") String categoryId);

    @FormUrlEncoded
    @POST(RemoteConstants.ADD_ITEM)
    Observable<Response<AddItem>> addItem(
            @Field("name") String name,
            @Field("category") String category,
            @Field("rate") String rate
    );
}