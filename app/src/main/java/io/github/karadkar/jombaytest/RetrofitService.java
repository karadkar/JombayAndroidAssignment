package io.github.karadkar.jombaytest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rnztx on 15/3/17.
 */

public class RetrofitService {
    private static RetrofitService instance;

    public static RetrofitService getInstance(){
        if(instance==null)
            instance = new RetrofitService();

        return instance;
    }
    private RetrofitService(){}

    private final String BASE_URL = "https://es-q.co/sample_api/";

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public <S> S createService(Class<S> serviceClass) {

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
