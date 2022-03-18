package com.ders.wheatherapp;

import android.content.Context;
import android.widget.Toast;

import com.ders.wheatherapp.Models.NewsApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getNewsHeadLines(OnFetchDataListener listener, String category, String query){
        CallNewsAPI callNewsAPI = retrofit.create(CallNewsAPI.class);

        Call<NewsApiResponse> call = callNewsAPI.callHeadLines("tr", category, query, context.getString(R.string.api_key));

        try{
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }

                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request Failed");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface  CallNewsAPI{
        @GET("top-headlines")
        Call<NewsApiResponse> callHeadLines(@Query("country") String country, @Query("category") String category, @Query("q") String q, @Query("apiKey") String key);
    }
}
