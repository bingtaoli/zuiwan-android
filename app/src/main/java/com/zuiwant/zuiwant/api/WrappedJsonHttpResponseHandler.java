package com.zuiwant.zuiwant.api;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.zuiwant.zuiwant.model.PersistenceHelper;
import com.zuiwant.zuiwant.model.ZuiwantModel;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by matthew on 16/4/14.
 */
class WrappedJsonHttpResponseHandler<T extends ZuiwantModel> extends JsonHttpResponseHandler {
    HttpRequestHandler<ArrayList<T>> handler;
    /**
     * 这样做复用也是666
     */
    Class c;
    Context context;
    String key;

    public WrappedJsonHttpResponseHandler(Context cxt, Class c, String key,
                                          HttpRequestHandler<ArrayList<T>> handler) {
        this.handler = handler;
        this.c = c;
        this.context = cxt;
        this.key = key;
    }

    /**
     * 像get_recommend这种接口返回的就是Object了
     * @param statusCode
     * @param headers
     * @param response
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        ArrayList<T> models = new ArrayList<T>();
        try {
            T obj = (T) Class.forName(c.getName()).newInstance();
            obj.parse(response);
            if (obj != null){
                models.add(obj);
            }
        } catch (Exception e) {
            //
        }
        //持久化
        PersistenceHelper.saveModelList(context, models, key);
        SafeHandler.onSuccess(handler, models);
    }

    /**
     * 默认response是一个JSONArray
     * 比如topics:
     * [
     *      {id: xx, topic_name: xxx, topic_intro: xxx},
     *      {id: xx, topic_name: xxx, topic_intro: xxx},
     * ]
     * 解析recommend是不一样的,因为get_recommend接口返回的json是:
     * {
     *     banner: [
     *          {id: xxx, article_title: xxx},
     *          {id: xxx, article_title: xxx},
     *     ],
     *     recommend: [
     *          {id: xxx, article_title: xxx},
     *          {id: xxx, article_title: xxx},
     *     ],
     *     recommendCount: int
     * } 则使用上面的函数进行处理
     * @param statusCode
     * @param headers
     * @param response
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        Log.d("lee", "json http response success, model type: " + c.getName());
        ArrayList<T> models = new ArrayList<T>();
        Log.d("lee", "json response length is " + response.length());
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObj = response.getJSONObject(i);
                T obj = (T) Class.forName(c.getName()).newInstance();
                obj.parse(jsonObj);
                if (obj != null)
                    models.add(obj);
            } catch (Exception e) {
            }
        }
        PersistenceHelper.saveModelList(context, models, key);
        SafeHandler.onSuccess(handler, models);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
        handleFailure(statusCode, e.getMessage());
    }

    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
        handleFailure(statusCode, e.getMessage());
    }

    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONArray errorResponse) {
        handleFailure(statusCode, e.getMessage());
    }

    private void handleFailure(int statusCode, String error) {
//        error = V2EXErrorType.errorMessage(context, V2EXErrorType.ErrorApiForbidden);
//        SafeHandler.onFailure(handler, error);
    }
}