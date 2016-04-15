package com.zuiwant.zuiwant.api;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.zuiwant.zuiwant.model.MediaModel;
import com.zuiwant.zuiwant.model.PersistenceHelper;
import com.zuiwant.zuiwant.model.TopicModel;

import java.util.ArrayList;

/**
 * 和后台交互的API
 * 获取topic, article, media等
 */
public class ZWManager {

    private static String getTopicsUrl = "http://zuiwant.com/zuiwan-backend/index.php/topic/get_topic";
    private static String getMediasUrl = "http://zuiwant.com/zuiwan-backend/index.php/media/get_media";
    private static String getRecommendsUrl = "http://zuiwant.com/zuiwan-backend/index.php/article/get_recommend";

    /**
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

    public static void getMedias(Context ctx, boolean refresh,
                                 final HttpRequestHandler<ArrayList<MediaModel>> handler){
        String key = "get_media";
        if (!refresh) {
            //尝试从缓存中加载
            ArrayList<MediaModel> medias = PersistenceHelper.loadModelList(ctx, key);
            if (medias != null && medias.size() > 0) {
                SafeHandler.onSuccess(handler, medias);
                return;
            }
        }
        new AsyncHttpClient().get(ctx, getMediasUrl,
                new WrappedJsonHttpResponseHandler<MediaModel>(ctx, MediaModel.class, key, handler));
    }


//    public static void getRecommends(Context ctx, boolean refresh,
//                                 final HttpRequestHandler<ArrayList<TopicModel>> handler){
//        String key = "get_recommend";
//        if (!refresh) {
//            //尝试从缓存中加载
//            ArrayList<TopicModel> topics = PersistenceHelper.loadModelList(ctx, key);
//            if (topics != null && topics.size() > 0) {
//                SafeHandler.onSuccess(handler, topics);
//                return;
//            }
//        }
//        new AsyncHttpClient().get(ctx, getRecommendsUrl,
//                new WrappedJsonHttpResponseHandler<TopicModel>(ctx, TopicModel.class, key, handler));
//    }



}
