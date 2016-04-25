package com.zuiwant.zuiwant.ui.fragment;

import com.zuiwant.zuiwant.model.MediaModel;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zuiwant.zuiwant.R;

import com.zuiwant.zuiwant.api.HttpRequestHandler;
import com.zuiwant.zuiwant.api.ZWManager;
import com.zuiwant.zuiwant.ui.adapter.BaseRecycleAdapter;
import com.zuiwant.zuiwant.ui.adapter.MediasAdapter;

import java.util.ArrayList;

/**
 * Created by matthew on 16/4/15.
 */
public class MediasFragment extends BaseFragment implements HttpRequestHandler<ArrayList<MediaModel>> {

    RecyclerView mRecyclerView;
    MediasAdapter mMediasAdapter;
    SwipeRefreshLayout mSwipeLayout;

    boolean mIsLoading;

    @Override
    protected int setRootViewResId() {
        return R.layout.fragment_medias;
    }

    @Override
    public void initView(){
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_medias);
        mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
    }

    @Override
    public void setViewStatus(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMediasAdapter = new MediasAdapter(getActivity());
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

        if (data.size() == 0) return;

        mMediasAdapter.insertAtBack(data, currentPage != 1);
    }

    @Override
    public void onFailure(String error) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;
    }
}