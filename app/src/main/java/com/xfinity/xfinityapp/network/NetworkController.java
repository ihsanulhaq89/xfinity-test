package com.xfinity.xfinityapp.network;

import com.google.gson.GsonBuilder;
import com.xfinity.xfinityapp.interfaces.CharacterService;
import com.xfinity.xfinityapp.models.CharacterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ihsanulhaq on 3/10/2016.
 */
public class NetworkController {
    public static final String BASE_URL = "http://api.duckduckgo.com/";

    public void getListOfCharacters(String q){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        CharacterService service = retrofit.create(CharacterService.class);
        Call<CharacterResponse> call = service.getListOfCharacters(q);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                int statusCode = response.code();
                CharacterResponse responseObj = response.body();

            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {

            }
        });
    }
}
