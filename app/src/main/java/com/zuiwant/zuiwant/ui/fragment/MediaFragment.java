package com.zuiwant.zuiwant.ui.fragment;

import com.zuiwant.zuiwant.model.MediaModel;

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

import com.zuiwant.zuiwant.api.HttpRequestHandler;
import com.zuiwant.zuiwant.api.ZWManager;
import com.zuiwant.zuiwant.ui.adapter.BaseRecycleAdapter;
import com.zuiwant.zuiwant.ui.adapter.MediasAdapter;
import com.zuiwant.zuiwant.ui.adapter.TopicsAdapter;

import java.util.ArrayList;

/**
 * Created by matthew on 16/4/15.
 */
public class MediaFragment extends BaseFragment implements HttpRequestHandler<ArrayList<MediaModel>> {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MediasAdapter mMediasAdapter;
    SwipeRefreshLayout mSwipeLayout;

    boolean mIsLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_medias, container, false);

        final Context context = getActivity();

        //TODO 这两步可以封在一个initView函数中,类似ListenerRain项目做的一样
        mSwipeLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.list_medias);

        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        //TODO 可以封在setViewStatus函数中
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMediasAdapter = new MediasAdapter(context);
        mRecyclerView.setAdapter(mMediasAdapter);

        mMediasAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
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
                requestMedias(true);
            }
        });

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mSwipeLayout.setRefreshing(true);
        requestMedias(false);
    }

    private void requestMedias(boolean refresh){
        if (mIsLoading){
            return ;
        }
        mIsLoading = true;
        ZWManager.getMedias(getActivity(), refresh, this);
    }

    @Override
    public void onSuccess(ArrayList<MediaModel> data) {
        onSuccess(data, 1, 1);
    }

    @Override
    public void onSuccess(ArrayList<MediaModel> data, int totalPages, int currentPage) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;

        //NOTE 先不实现分页

        if (data.size() == 0) return;

        mMediasAdapter.insertAtBack(data, currentPage != 1);
    }

    @Override
    public void onFailure(String error) {
        //
    }
}