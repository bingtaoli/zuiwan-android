package com.zuiwant.zuiwant.api;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.zuiwant.zuiwant.model.ArticleContentModel;
import com.zuiwant.zuiwant.model.ArticleModel;
import com.zuiwant.zuiwant.model.MediaModel;
import com.zuiwant.zuiwant.model.PersistenceHelper;
import com.zuiwant.zuiwant.model.RecommendPageModel;
import com.zuiwant.zuiwant.model.TopicDetailModel;
import com.zuiwant.zuiwant.model.TopicModel;

import java.util.ArrayList;

/**
 * 和后台交互的API
 * 获取topic, article, media等
 */
public class ZWManager {

    private static String zuiwant = "http://zuiwant.com/zuiwan-backend/index.php/";
    private static String getTopicsUrl =  zuiwant + "topic/get_topic";
    private static String getMediasUrl =  zuiwant + "media/get_media";

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
                new WrappedJsonHttpResponseHandler<>(ctx, TopicModel.class, key, handler));
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
                new WrappedJsonHttpResponseHandler<>(ctx, MediaModel.class, key, handler));
    }

    public static void getRecommends(Context ctx, boolean refresh, int page,
                                 final HttpRequestHandler<ArrayList<RecommendPageModel>> handler){
        String key = "get_recommend_page_" + page;
        if (!refresh) {
            ArrayList<RecommendPageModel> recommends = PersistenceHelper.loadModelList(ctx, key);
            if (recommends != null && recommends.size() > 0) {
                SafeHandler.onSuccess(handler, recommends);
                return;
            }
        }
        String getRecommendsUrl = zuiwant + "article/get_recommend?page=" + page;
        new AsyncHttpClient().get(ctx, getRecommendsUrl,
                new WrappedJsonHttpResponseHandler<>(ctx, RecommendPageModel.class, key, handler));
    }

    public static void getBanner(Context ctx, boolean refresh, int page,
    final HttpRequestHandler<ArrayList<ArticleModel>> handler){
        String key = "get_recommend_page_" + page;
        if (!refresh) {
            //尝试从缓存中加载
            ArrayList<ArticleModel> recommends = PersistenceHelper.loadModelList(ctx, key);
            if (recommends != null && recommends.size() > 0) {
                SafeHandler.onSuccess(handler, recommends);
                return;
            }
        }
        String getRecommendsUrl = zuiwant + "article/get_recommend_articles?page=" + page;
        new AsyncHttpClient().get(ctx, getRecommendsUrl,
                new WrappedJsonHttpResponseHandler<>(ctx, ArticleModel.class, key, handler));
    }

    public static void getOneArticle(Context ctx, int articleId, boolean refresh,
                                     final HttpRequestHandler<ArrayList<ArticleContentModel>> handler){
        String key = "get_article" + articleId;
        String getOneArticleUrl = zuiwant + "article/get_one_article?id=" + articleId;
        if (!refresh) {
            //尝试从缓存中加载
            ArrayList<ArticleContentModel> recommends = PersistenceHelper.loadModelList(ctx, key);
            if (recommends != null && recommends.size() > 0) {
                SafeHandler.onSuccess(handler, recommends);
                return;
            }
        }
        new AsyncHttpClient().get(ctx, getOneArticleUrl,
                new WrappedJsonHttpResponseHandler<>(ctx, ArticleContentModel.class, key, handler));
    }

    public static void getTopicArticles(Context ctx, int topicId, boolean refresh,
                                     final HttpRequestHandler<ArrayList<TopicDetailModel>> handler){
        String key = "get_topic_article" + topicId;
        String url = zuiwant + "topic/get_one_topic?id=" + topicId;
        if (!refresh) {
            //尝试从缓存中加载
            //TODO 其实肯定只有一个TopicDetailModel返回,所以onSuccess可能以后会改
            ArrayList<TopicDetailModel> topicDetails = PersistenceHelper.loadModelList(ctx, key);
            if (topicDetails != null && topicDetails.size() > 0) {
                SafeHandler.onSuccess(handler, topicDetails);
                return;
            }
        }
        new AsyncHttpClient().get(ctx, url,
                new WrappedJsonHttpResponseHandler<>(ctx, TopicDetailModel.class, key, handler));
    }


}
