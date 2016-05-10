package com.zuiwant.zuiwant.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Created by matthew on 16/5/10.
 * 称之为Recommend Page Model是有原因的,因为后端给的数据是为了哪一个页面考虑的
 */
public class RecommendPageModel extends ZuiwantModel {
    private static final long serialVersionUID = 2015050105L;

    public ArrayList<ArticleModel> recommend = new ArrayList<>();
    public ArrayList<ArticleModel> banner = new ArrayList<>();

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        //recommend
        JSONArray jsonArray = jsonObject.getJSONArray("recommend");
        for (int i = 0; i < jsonArray.length(); i++){
            ArticleModel article = new ArticleModel();
            article.parse(jsonArray.getJSONObject(i));
            recommend.add(article);
        }
        //banner
        //jsonObject does not have banner when page > 1
        if (jsonObject.has("banner")){
            JSONArray bannerJsonArray = jsonObject.getJSONArray("banner");
            for (int i = 0; i < bannerJsonArray.length(); i++){
                ArticleModel article = new ArticleModel();
                article.parse(bannerJsonArray.getJSONObject(i));
                banner.add(article);
            }
        }
    }
}
