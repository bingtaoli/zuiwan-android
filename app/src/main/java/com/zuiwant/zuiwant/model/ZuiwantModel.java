package com.zuiwant.zuiwant.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by libingtao on 16/4/13.
 */
public abstract class ZuiwantModel implements Serializable {

    private static final long serialVersionUID = 2015050101L;

    abstract public void parse(JSONObject jsonObject) throws JSONException;

}
