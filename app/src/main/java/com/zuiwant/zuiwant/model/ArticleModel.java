package com.zuiwant.zuiwant.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matthew on 16/4/15.
 */
public class ArticleModel extends ZuiwantModel {

    public int id;
    public String articleMediaName;
    public String articleTitle;
    public String articleTopicName;
    public String articleImg;

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        articleMediaName = jsonObject.getString("article_media_name");
        articleTitle = jsonObject.getString("article_title");
        articleTopicName = jsonObject.getString("article_topic_name");
        articleImg = jsonObject.getString("article_img");
    }

}
