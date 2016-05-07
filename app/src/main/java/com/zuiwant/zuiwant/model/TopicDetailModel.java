package com.zuiwant.zuiwant.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Created by matthew on 16/5/7.
 * TopicDetailModel是为了适应后端API而创建的类
 */
public class TopicDetailModel extends ZuiwantModel {

    private static final long serialVersionUID = 2015050105L;
    public int articleCount;
    public ArrayList<ArticleModel> articles = new ArrayList<>();
    public String topicImg;
    public String topicIntro;
    public String topicName;

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        articleCount = jsonObject.getInt("article_count");
        topicImg = jsonObject.getString("topic_img");
        topicIntro = jsonObject.getString("topic_intro");
        topicName = jsonObject.getString("topic_name");
        //DONE 获取文章
        JSONArray jsonArray = jsonObject.getJSONArray("articles");
        for (int i = 0; i < jsonArray.length(); i++){
            ArticleModel article = new ArticleModel();
            article.parse(jsonArray.getJSONObject(i));
            articles.add(article);
        }
    }
}
