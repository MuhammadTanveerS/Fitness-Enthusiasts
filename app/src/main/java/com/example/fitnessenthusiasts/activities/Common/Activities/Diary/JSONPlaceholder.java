package com.example.fitnessenthusiasts.activities.Common.Activities.Diary;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceholder {

    @GET("posts")
    Call<List<Post>> getPost();

    @GET("posts/{id}/comments")
    Call<List<Post>> getComments(@Path("id") int postId);

    @Headers({"x-app-id: b5005fad", "x-app-key: 0cbd64a1e105bfa42abd4f6579214d24"})
    @GET("search/instant")
    Call<JsonObject> getSearchNutritions(@Query("query") String query);

}
