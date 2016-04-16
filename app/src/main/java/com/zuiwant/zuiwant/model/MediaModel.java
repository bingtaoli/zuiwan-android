package com.zuiwant.zuiwant.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matthew on 16/4/15.
 */
public class MediaModel extends ZuiwantModel {

    private static final long serialVersionUID = 2015050105L;

    public int id;
    public String mediaName;
    public int articleNum = 0;
    public String mediaIntro;
    public String mediaAvatar;

    public MediaModel(){}

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        //articleNum = jsonObject.getInt("article_count");
        mediaName = jsonObject.getString("media_name");
        mediaIntro = jsonObject.getString("media_intro");
        mediaAvatar = jsonObject.getString("media_avatar");
    }
}
