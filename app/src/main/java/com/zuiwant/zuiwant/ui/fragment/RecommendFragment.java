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
import com.zuiwant.zuiwant.model.ArticleModel;
import com.zuiwant.zuiwant.ui.adapter.ArticleAdapter;
import com.zuiwant.zuiwant.ui.adapter.BaseRecycleAdapter;

import java.util.ArrayList;

/**
 * Created by matthew on 16/4/17.
 */
public class RecommendFragment extends BaseFragment implements HttpRequestHandler<ArrayList<ArticleModel>> {

    RecyclerView mRecyclerView;
    ArticleAdapter mArticleAdapter;
    SwipeRefreshLayout mSwipeLayout;
    private ArrayList<ArticleModel> recommends = new ArrayList<>();

    boolean mIsLoading; //是否在加载

    @Override
    protected int setRootViewResId() {
        return R.layout.fragment_recommends;
    }

    @Override
    public void initView(){
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_recommends);
        mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
    }

    @Override
    public void setViewStatus(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mArticleAdapter = new ArticleAdapter(getActivity(), recommends);
        mRecyclerView.setAdapter(mArticleAdapter);

        mArticleAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
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
                requestRecommends(true);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mSwipeLayout.setRefreshing(true);
        requestRecommends(false);
    }

    private void requestRecommends(boolean refresh){
        if (mIsLoading){
            return ;
        }
        mIsLoading = true;
        ZWManager.getRecommends(getActivity(), refresh, this);
    }

    @Override
    public void onSuccess(ArrayList<ArticleModel> data) {
        onSuccess(data, 1, 1);
    }

    @Override
    public void onSuccess(ArrayList<ArticleModel> data, int totalPages, int currentPage) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;

        //NOTE 先不实现分页

        if (data.size() == 0) return;

        mArticleAdapter.insertAtBack(data, currentPage != 1);
    }

    @Override
    public void onFailure(String error) {
        //
    }
}
