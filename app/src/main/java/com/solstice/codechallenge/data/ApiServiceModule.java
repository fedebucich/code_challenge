package com.solstice.codechallenge.data;

import com.solstice.codechallenge.data.api.ContactService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fbucich on 1/14/18.
 */
@Module
public class ApiServiceModule {
    private final String BASE_URL = "https://s3.amazonaws.com/technical-challenge/v3/";

    @Provides
    @Named(BASE_URL)
    String provideBaseUrl() {
        return BASE_URL;
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    Converter.Factory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    CallAdapter.Factory provideRxJavaAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@Named(BASE_URL) String baseUrl, Converter.Factory converterFactory,
                             CallAdapter.Factory callAdapterFactory, OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    ContactService provideContactService(Retrofit retrofit) {
        return retrofit.create(ContactService.class);
    }
}
