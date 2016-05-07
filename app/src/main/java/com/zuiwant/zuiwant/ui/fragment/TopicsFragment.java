package com.zuiwant.zuiwant.ui.fragment;

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTopicAdapter = new TopicsAdapter(getActivity());
        mRecyclerView.setAdapter(mTopicAdapter);

        mTopicAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //打开一个新的activity
                //暂时只记录log
                Log.d("lee", "one item is clicked");
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("lee", "on refreshing");
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
        Log.d("lee", "topic fragment on success");
        Log.d("lee", "data goes to topic fragment length is " + data.size());
        onSuccess(data, 1, 1);
    }

    @Override
    public void onSuccess(ArrayList<TopicModel> data, int totalPages, int currentPage) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;

        if (data.size() == 0) return;

        mTopicAdapter.insertAtBack(data, currentPage != 1);
    }

    @Override
    public void onFailure(String error) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;
    }
}
