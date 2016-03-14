package com.xfinity.xfinityapp.models;

/**
 * Created by Ihsanulhaq on 3/11/2016.
 */

import java.util.ArrayList;
import java.util.List;

public class CharacterResponse {

    private String Heading;
    private List<RelatedTopic> RelatedTopics = new ArrayList<RelatedTopic>();

    /**
     * @return The Heading
     */
    public String getHeading() {
        return Heading;
    }

    /**
     * @param Heading The Heading
     */
    public void setHeading(String Heading) {
        this.Heading = Heading;
    }

    /**
     * @return The RelatedTopics
     */
    public List<RelatedTopic> getRelatedTopics() {
        return RelatedTopics;
    }

    /**
     * @param RelatedTopics The RelatedTopics
     */
    public void setRelatedTopics(List<RelatedTopic> RelatedTopics) {
        this.RelatedTopics = RelatedTopics;
    }

}
