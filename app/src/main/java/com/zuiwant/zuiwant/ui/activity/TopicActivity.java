package com.zuiwant.zuiwant.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.api.HttpRequestHandler;
import com.zuiwant.zuiwant.model.ArticleModel;

import java.util.ArrayList;

/*
 * Created by matthew on 16/5/7.
 */
public class TopicActivity extends AppCompatActivity
        implements HttpRequestHandler<ArrayList<ArticleModel>> {

    private ArrayList<ArticleModel> topicArticles = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
    }

    @Override
    public void onSuccess(ArrayList<ArticleModel> data) {

    }

    @Override
    public void onSuccess(ArrayList<ArticleModel> data, int totalPages, int currentPage) {

    }

    @Override
    public void onFailure(String error) {

    }
}
