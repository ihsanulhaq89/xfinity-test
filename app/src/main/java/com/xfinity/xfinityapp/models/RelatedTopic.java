package com.xfinity.xfinityapp.models;

import android.database.sqlite.SQLiteException;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ihsanulhaq on 3/12/2016.
 */

public class RelatedTopic extends SugarRecord implements Serializable {

    private String Result;
    private Icon Icon;
    private String FirstURL;
    private String Text;
    private boolean favorite;

    /**
     * @return The Result
     */
    public String getResult() {
        return Result;
    }

    /**
     * @param Result The Result
     */
    public void setResult(String Result) {
        this.Result = Result;
    }

    /**
     * @return The Icon
     */
    public Icon getIcon() {
        return Icon;
    }

    public Icon getIconFromDB() {
        Icon = com.xfinity.xfinityapp.models.Icon.findById(com.xfinity.xfinityapp.models.Icon.class, getId());
        return Icon;
    }

    /**
     * @param Icon The Icon
     */
    public void setIcon(Icon Icon) {
        this.Icon = Icon;
    }

    /**
     * @return The FirstURL
     */
    public String getFirstURL() {
        return FirstURL;
    }

    /**
     * @param FirstURL The FirstURL
     */
    public void setFirstURL(String FirstURL) {
        this.FirstURL = FirstURL;
    }

    /**
     * @return The Text
     */
    public String getText() {

        return Text;
    }

    /**
     * @param Text The Text
     */
    public void setText(String Text) {
        this.Text = Text;
    }

    public String getDescription() {
        return Text.substring(getText().indexOf("-") + 2);
    }

    public String getTitle() {
        return Text.substring(0, getText().indexOf("-") - 1);
    }

    public boolean getFavorite() {
        return favorite;
    }

    /**
     * @param favorite The favorite
     */
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}