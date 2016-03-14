package com.xfinity.xfinityapp.models;

import java.io.Serializable;

/**
 * Created by Ihsanulhaq on 3/12/2016.
 */

public class RelatedTopic implements Serializable{

    private String Result;
    private Icon Icon;
    private String FirstURL;
    private String Text;

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
    public com.xfinity.xfinityapp.models.Icon getIcon() {
        return Icon;
    }

    /**
     * @param Icon The Icon
     */
    public void setIcon(com.xfinity.xfinityapp.models.Icon Icon) {
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

    public String getDescription(){
        return Text.substring(getText().indexOf("-")+2);
    }

    public String getTitle(){
        return Text.substring(0, getText().indexOf("-")-1);
    }
}