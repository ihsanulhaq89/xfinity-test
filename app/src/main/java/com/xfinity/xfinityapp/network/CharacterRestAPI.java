package com.xfinity.xfinityapp.network;

import com.google.gson.GsonBuilder;
import com.xfinity.xfinityapp.interfaces.CharacterRestAPIListener;
import com.xfinity.xfinityapp.interfaces.CharacterService;
import com.xfinity.xfinityapp.models.CharacterResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ihsanulhaq on 3/10/2016.
 */
public class CharacterRestAPI implements Callback<CharacterResponse> {

    private static final String BASE_URL = "http://api.duckduckgo.com";
    private CharacterRestAPIListener listener;
    private CharacterService service;

    public CharacterRestAPI(CharacterRestAPIListener listener) {
        this.listener = listener;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        service = retrofit.create(CharacterService.class);
        this.listener = listener;
    }

    public void getListOfGOTCharacters() {
        Call<CharacterResponse> call = null;
        call = service.getListOfGOTCharacters();
        call.enqueue(this);
    }

    public void getListOfSimpsonsCharacters() {
        Call<CharacterResponse> call = null;
        call = service.getListOfSimpsonsCharacters();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
        int statusCode = response.code();
        CharacterResponse responseObj = response.body();
        listener.onSuccess(responseObj);
    }

    @Override
    public void onFailure(Call<CharacterResponse> call, Throwable t) {
        listener.onFailure(t.getLocalizedMessage());
    }

}
