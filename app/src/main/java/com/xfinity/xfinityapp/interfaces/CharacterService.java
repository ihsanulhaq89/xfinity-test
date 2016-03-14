package com.xfinity.xfinityapp.interfaces;

import com.xfinity.xfinityapp.models.CharacterResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Ihsanulhaq on 3/11/2016.
 */
public interface CharacterService {
    @GET("/?q=game+of+thrones+characters&format=json")
    Call<CharacterResponse> getListOfGOTCharacters();

    @GET("/?q=simpsons+characters&format=json")
    Call<CharacterResponse> getListOfSimpsonsCharacters();
}
