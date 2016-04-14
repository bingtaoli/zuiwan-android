package com.zuiwant.zuiwant.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zuiwant.zuiwant.R;

import com.zuiwant.zuiwant.model.TopicModel;
import com.zuiwant.zuiwant.ui.adapter.BaseRecycleAdapter;
import com.zuiwant.zuiwant.ui.adapter.TopicsAdapter;

import java.util.ArrayList;

/**
 * Created by libingtao on 16/4/13.
 */
public class TopicFragment extends BaseFragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TopicsAdapter mTopicAdapter;
    SwipeRefreshLayout mSwipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_topics, container, false);

        final Context context = getActivity();

        ArrayList<TopicModel> testList = new ArrayList<>();
        testList.add(new TopicModel(true));
        testList.add(new TopicModel(true));
        testList.add(new TopicModel(true));
        testList.add(new TopicModel(true));

        //TODO 这两步可以封在一个initView函数中,类似ListenerRain项目做的一样
        mSwipeLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.list_topics);

        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        //TODO 可以封在setViewStatus函数中
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTopicAdapter = new TopicsAdapter(context, testList);
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
                //
            }
        });

        return layout;
    }
}
