package com.zuiwant.zuiwant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.api.HttpRequestHandler;
import com.zuiwant.zuiwant.api.ZWManager;
import com.zuiwant.zuiwant.model.ArticleContentModel;
import com.zuiwant.zuiwant.model.ArticleModel;

import java.util.ArrayList;

/**
 * Created by matthew on 16/4/23.
 */
public class ArticleActivity extends AppCompatActivity implements HttpRequestHandler<ArrayList<ArticleContentModel>> {

    public ArticleModel article;
    public int articleId;
    private WebView mWebView;
    SwipeRefreshLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        initIntentParam(getIntent());
        /**
         * no need for article fragment, use article activity to show ui
         */
        setContentView(R.layout.article_content);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("lee", "on refreshing article content");
                requestArticleContent(true);
            }
        });
        mSwipeLayout.setRefreshing(true);

        mWebView = (WebView) findViewById(R.id.webview);
        String html = "精彩内容马上达到 : )";
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);

        requestArticleContent(false);
    }

    public void initIntentParam(Intent intent){
        article = (ArticleModel) intent.getSerializableExtra("article");
        if (article != null){
            articleId = article.id;
        } else {
            articleId = 0;
        }
    }

    public void requestArticleContent(boolean refresh){
        if (articleId != 0){
            ZWManager.getOneArticle(this, articleId, refresh, this);
        } else {
            Log.d("lee", "wrong article id");
        }
    }

    @Override
    public void onSuccess(ArrayList<ArticleContentModel> data) {
        mSwipeLayout.setRefreshing(false);
        if (data.size() == 1){
            String content = data.get(0).articleContent;
            String formatContent = ArticleContentModel.formatContent(content);
            String html = ArticleContentModel.getHtml(formatContent);
            mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
        } else {
            Log.d("lee", "get article content fail");
        }
    }

    @Override
    public void onSuccess(ArrayList<ArticleContentModel> data, int totalPages, int currentPage) {

    }

    @Override
    public void onFailure(String error) {

    }
}
