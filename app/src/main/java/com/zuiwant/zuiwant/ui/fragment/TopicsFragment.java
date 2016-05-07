package com.zuiwant.zuiwant.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zuiwant.zuiwant.R;

import com.zuiwant.zuiwant.api.HttpRequestHandler;
import com.zuiwant.zuiwant.api.ZWManager;
import com.zuiwant.zuiwant.model.TopicModel;
import com.zuiwant.zuiwant.ui.activity.ArticleActivity;
import com.zuiwant.zuiwant.ui.activity.TopicActivity;
import com.zuiwant.zuiwant.ui.adapter.BaseRecycleAdapter;
import com.zuiwant.zuiwant.ui.adapter.TopicsAdapter;

import java.util.ArrayList;

/*
 * Created by matthew on 16/4/13.
 */
public class TopicsFragment extends BaseFragment implements HttpRequestHandler<ArrayList<TopicModel>> {

    RecyclerView mRecyclerView;
    TopicsAdapter mTopicAdapter;
    SwipeRefreshLayout mSwipeLayout;
    ArrayList<TopicModel> topics = new ArrayList<>();

    boolean mIsLoading; //是否在加载

    @Override
    protected int setRootViewResId() {
        return R.layout.fragment_topics;
    }

    @Override
    public void initView(){
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_topics);
        mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
    }

    @Override
    public void setViewStatus(){
        // TODO 为什么一定要设置LayoutManager呢?不设置就会报错
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTopicAdapter = new TopicsAdapter(getActivity(), topics);
        mRecyclerView.setAdapter(mTopicAdapter);

        mTopicAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), TopicActivity.class);
                intent.putExtra("topicId", topics.get(position).id);
                getActivity().startActivity(intent);
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestTopics(true);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mSwipeLayout.setRefreshing(true);
        requestTopics(false);
    }

    private void requestTopics(boolean refresh){
        if (mIsLoading){
            return ;
        }
        mIsLoading = true;
        ZWManager.getTopics(getActivity(), refresh, this);
    }

    @Override
    public void onSuccess(ArrayList<TopicModel> data) {
        onSuccess(data, 1, 1);
    }

    @Override
    public void onSuccess(ArrayList<TopicModel> data, int totalPages, int currentPage) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;
        if (data.size() == 0) return;
        topics.addAll(data);
        mTopicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String error) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;
    }
}
