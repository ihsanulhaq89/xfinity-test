package com.xfinity.xfinityapp.models;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Ihsanulhaq on 3/12/2016.
 */

public class Icon extends SugarRecord implements Serializable {

    private String URL;
    private String Height;
    private String Width;

    /**
     * @return The URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @param URL The URL
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * @return The Height
     */
    public String getHeight() {
        return Height;
    }

    /**
     * @param Height The Height
     */
    public void setHeight(String Height) {
        this.Height = Height;
    }

    /**
     * @return The Width
     */
    public String getWidth() {
        return Width;
    }

    /**
     * @param Width The Width
     */
    public void setWidth(String Width) {
        this.Width = Width;
    }

}