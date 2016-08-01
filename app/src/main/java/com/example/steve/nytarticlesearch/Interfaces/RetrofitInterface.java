package com.example.steve.nytarticlesearch.Interfaces;

import com.example.steve.nytarticlesearch.Models.Response;
import com.example.steve.nytarticlesearch.Models.RetrofitResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetrofitInterface {

    //get
    @GET("/svc/search/v2/articlesearch.json?")
    Call<RetrofitResponse> getResponse(@Query("api-key") String apiKey,
                                       @Query("q") String query,
                                       @Query("page") int page);

    @GET("/svc/search/v2/articlesearch.json?")
    Call<RetrofitResponse> getResponse(@Query("api-key") String apiKey,
                                       @Query("q") String query,
                                       @Query("page") int page,
                                       @Query("fq") String filterQuery,
                                       @Query("sort") String sort);
    //no filter query.
    @GET("/svc/search/v2/articlesearch.json?")
    Call<RetrofitResponse> getResponse(@Query("api-key") String apiKey,
                                       @Query("q") String query,
                                       @Query("page") int page,
                                       @Query("sort") String sort);

    @GET("/svc/search/v2/articlesearch.json?")
    Call<RetrofitResponse> getResponse(@Query("api-key") String apiKey,
                                       @Query("q") String query,
                                       @Query("page") int page,
                                       @Query("sort") String sort,
                                       @Query("begin_date") Integer beginDate);

    @GET("/svc/search/v2/articlesearch.json?")
    Call<RetrofitResponse> getResponse(@Query("api-key") String apiKey,
                                       @Query("q") String query,
                                       @Query("page") int page,
                                       @Query("fq") String filterQuery,
                                       @Query("sort") String sort,
                                       @Query("begin_date") Integer beginDate);

}
