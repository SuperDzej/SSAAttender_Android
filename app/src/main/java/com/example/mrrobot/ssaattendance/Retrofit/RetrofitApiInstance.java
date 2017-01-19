package com.example.mrrobot.ssaattendance.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MrRobot on 10/25/2016.
 *
 */

public class RetrofitApiInstance {
    private static Retrofit retrofit = null;
    public RetrofitApiInstance (){

    }

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

            final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(logging);

            OkHttpClient okHttpClient = okHttpClientBuilder.build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            return  new Retrofit.Builder()
                    .baseUrl("https://api.myjson.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
