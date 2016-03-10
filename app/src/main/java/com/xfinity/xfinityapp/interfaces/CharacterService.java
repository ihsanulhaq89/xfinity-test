package com.xfinity.xfinityapp.interfaces;

import com.xfinity.xfinityapp.models.CharacterResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ihsanulhaq on 3/11/2016.
 */
public interface CharacterService {
    @GET("/?format=json")
    Call<CharacterResponse> getListOfCharacters(@Query("q") String q);
}
