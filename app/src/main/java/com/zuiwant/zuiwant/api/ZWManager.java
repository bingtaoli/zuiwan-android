package com.zuiwant.zuiwant.api;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.zuiwant.zuiwant.model.PersistenceHelper;
import com.zuiwant.zuiwant.model.TopicModel;

import java.util.ArrayList;

/**
 * 和后台交互的API
 * 获取topic, article, media等
 */
public class ZWManager {

    private static String getTopicsUrl = "http://zuiwant.com/zuiwan-backend/index.php/topic/get_topic";

    /**
     *
     * @param ctx
     * @param refresh 是否刷新
     * @param handler 结果处理
     */
    public static void getTopics(Context ctx, boolean refresh,
                            final HttpRequestHandler<ArrayList<TopicModel>> handler){
        String key = "get_topic";
        if (!refresh) {
            //尝试从缓存中加载
            ArrayList<TopicModel> topics = PersistenceHelper.loadModelList(ctx, key);
            if (topics != null && topics.size() > 0) {
                SafeHandler.onSuccess(handler, topics);
                return;
            }
        }
        new AsyncHttpClient().get(ctx, getTopicsUrl,
                new WrappedJsonHttpResponseHandler<TopicModel>(ctx, TopicModel.class, key, handler));
    }

}
