package com.xfinity.xfinityapp.interfaces;

import com.xfinity.xfinityapp.models.CharacterResponse;

/**
 * Created by Ihsanulhaq on 3/12/2016.
 */
public interface CharacterRestAPIListener {
    void onSuccess(CharacterResponse data);

    void onFailure(String errorMsg);
}
