package com.zuiwant.zuiwant.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matthew on 16/4/23.
 */
public class ArticleContentModel extends ZuiwantModel  {

    public String articleAuthor;
    public String articleContent;
    public String articleImg;
    public String articleTitle;
    public String createTime;

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        articleAuthor = jsonObject.getString("article_author");
        articleContent = jsonObject.getString("article_content");
        articleImg = jsonObject.getString("article_img");
        articleTitle = jsonObject.getString("article_title");
        createTime = jsonObject.getString("create_time");
    }

    public static String formatContent(String content){
        //TODO 正则把图片src补上zuiwant.com
        return content;
    }

}
