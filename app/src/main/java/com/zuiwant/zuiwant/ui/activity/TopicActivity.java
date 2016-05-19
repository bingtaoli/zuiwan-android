package com.zuiwant.zuiwant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.api.HttpRequestHandler;
import com.zuiwant.zuiwant.api.ZWManager;
import com.zuiwant.zuiwant.model.ArticleModel;
import com.zuiwant.zuiwant.model.TopicDetailModel;
import com.zuiwant.zuiwant.ui.adapter.TopicArticlesAdapter;

import java.util.ArrayList;

/*
 * Created by matthew on 16/5/7.
 */
public class TopicActivity extends AppCompatActivity
        implements HttpRequestHandler<ArrayList<TopicDetailModel>> {

    private TopicDetailModel topicDetail = new TopicDetailModel();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TopicArticlesAdapter adapter;
    private Boolean isLoading = false;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initIntentParam(getIntent());

        setContentView(R.layout.activity_topic_detail);

        adapter = new TopicArticlesAdapter(this, topicDetail);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestTopicArticles(true);
            }
        });
        swipeRefreshLayout.setRefreshing(true);

        recyclerView = (RecyclerView) findViewById(R.id.topics_articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
        requestTopicArticles(true);
    }

    public void initIntentParam(Intent intent){
        id = intent.getIntExtra("topicId", 23);
    }

    private void requestTopicArticles(boolean refresh){
        if (isLoading){
            return;
        }
        isLoading = true;
        //DONE topicId argument
        ZWManager.getTopicArticles(this, id, refresh, this);
    }

    @Override
    public void onSuccess(ArrayList<TopicDetailModel> data) {
        onSuccess(data, 0, 0);
    }

    @Override
    public void onSuccess(ArrayList<TopicDetailModel> data, int totalPages, int currentPage) {
        //为topicDetail赋值
        topicDetail.set(data.get(0));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String error) {
        swipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }
}
