package com.zuiwant.zuiwant.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.api.HttpRequestHandler;
import com.zuiwant.zuiwant.api.ZWManager;
import com.zuiwant.zuiwant.model.ArticleModel;
import com.zuiwant.zuiwant.ui.activity.ArticleActivity;
import com.zuiwant.zuiwant.ui.adapter.ArticlesAdapter;
import com.zuiwant.zuiwant.ui.adapter.BaseRecycleAdapter;

import java.util.ArrayList;

/**
 * Created by matthew on 16/4/17.
 */
public class RecommendArticlesFragment extends BaseFragment implements HttpRequestHandler<ArrayList<ArticleModel>> {

    private static final int PRELOAD_SIZE = 4; //已经加载
    RecyclerView mRecyclerView;
    ArticlesAdapter mArticleAdapter;
    SwipeRefreshLayout mSwipeLayout;

    private int mPage = 1;
    private boolean mIsFirstTimeTouchBottom = true;

    boolean mIsLoading; //是否在加载

    /**
     * 两栏布局,所以不使用基类的onCreateView :(
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_recommends, container, false);
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.list_recommends);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(getOnBottomListener(layoutManager));

        mArticleAdapter = new ArticlesAdapter(getActivity());
        mRecyclerView.setAdapter(mArticleAdapter);

        mSwipeLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        return layout;
    }

    @Override
    public void setViewStatus() {

        mArticleAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //打开一个新的activity
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                Log.d("lee", "start article activity");
                intent.putExtra("article", mArticleAdapter.getArticles().get(position));
                getActivity().startActivity(intent);
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("lee", "on refreshing");
                requestRecommends(true, mPage);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mSwipeLayout.setRefreshing(true);
        requestRecommends(false, mPage);
    }

    private void requestRecommends(boolean refresh, int page){
        if (mIsLoading){
            return ;
        }
        mIsLoading = true;
        ZWManager.getRecommends(getActivity(), refresh, page, this);
    }

    @Override
    public void onSuccess(ArrayList<ArticleModel> data) {
        /**
         * recommend不用add,因为在adapter中的articles是对recommend的引用
         * 如果recommend也加一遍,则每次更新都会增加两次!
         */
        onSuccess(data, mPage, mPage);
    }

    @Override
    public void onSuccess(ArrayList<ArticleModel> data, int totalPages, int currentPage) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;

        if (data.size() == 0) return;

        mArticleAdapter.insertAtBack(data, currentPage != 1);
    }

    @Override
    public void onFailure(String error) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;
    }

    RecyclerView.OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView rv, int dx, int dy) {
                //最后一个可见的item位置 >= 总数目 - 提前加载数目
                boolean isBottom = layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1]
                        >= mArticleAdapter.getItemCount() - PRELOAD_SIZE;
                if (!mSwipeLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
                        mSwipeLayout.setRefreshing(true);
                        mPage += 1;
                        Log.d("lee", "request page: " + mPage);
                        requestRecommends(true, mPage);
                    }
                    else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }
}
