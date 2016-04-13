package com.zuiwant.zuiwant.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zuiwant.zuiwant.R;

import com.zuiwant.zuiwant.model.TopicModel;
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

        mTopicAdapter = new TopicsAdapter(context, testList);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.list_topics);

        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mTopicAdapter);

        mSwipeLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //
            }
        });

        return layout;
    }


}
