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
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.api.HttpRequestHandler;
import com.zuiwant.zuiwant.api.ZWManager;
import com.zuiwant.zuiwant.model.ArticleModel;
import com.zuiwant.zuiwant.model.RecommendPageModel;
import com.zuiwant.zuiwant.ui.activity.ArticleActivity;
import com.zuiwant.zuiwant.ui.adapter.ArticlesAdapter;
import com.zuiwant.zuiwant.ui.adapter.BaseRecycleAdapter;
import com.zuiwant.zuiwant.ui.widget.BannerHolderView;

import java.util.ArrayList;

/*
 * Created by matthew on 16/4/30.
 */
public class RecommendFragment extends BaseFragment implements HttpRequestHandler<ArrayList<RecommendPageModel>> {

    private static final int PRELOAD_SIZE = 4; //已经加载
    RecyclerView mRecyclerView;
    View mHeaderView;
    ConvenientBanner mBanner;
    ArticlesAdapter mArticleAdapter;
    SwipeRefreshLayout mSwipeLayout;
    private ArrayList<ArticleModel> articles = new ArrayList<>();

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
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_recommends, container, false);

        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.list_recommends);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(getOnBottomListener(layoutManager));

        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.view_header, mRecyclerView, false);
        mBanner = (ConvenientBanner) mHeaderView.findViewById(R.id.id_banner_first);
        mBanner.setPageIndicator(new int[]{R.drawable.point_nomal, R.drawable.point_focured})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);

        mArticleAdapter = new ArticlesAdapter(getActivity(), articles);

        mRecyclerView.setAdapter(mArticleAdapter);

        mArticleAdapter.setHeaderView(mHeaderView);

        mSwipeLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);

        return layout;
    }

    @Override
    public void setViewStatus() {

        mArticleAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                int realPosition = position - 1;
                intent.putExtra("article", articles.get(realPosition));
                getActivity().startActivity(intent);
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestRecommends(true);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mSwipeLayout.setRefreshing(true);
        //TODO 网络好的时候true
        requestRecommends(false);
    }

    //刷新,一般是在顶部
    private void requestRecommends(boolean refresh){
        mPage = 1;
        if (mIsLoading){
            return ;
        }
        mIsLoading = true;
        ZWManager.getRecommends(getActivity(), refresh, 1, this);
    }

    private void loadMore(){
        if (mIsLoading){
            return ;
        }
        mIsLoading = true;
        ZWManager.getRecommends(getActivity(), true, mPage, this);
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
                        loadMore();
                    }
                    else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

    @Override
    public void onSuccess(ArrayList<RecommendPageModel> data) {
        onSuccess(data, mPage, mPage);
    }

    @Override
    public void onSuccess(ArrayList<RecommendPageModel> data, int totalPages, int currentPage) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;

        if (data.size() == 0) return;

        if (mPage == 1){
            //刷新,不是loadMore
            articles.clear();
        }
        articles.addAll(data.get(0).recommend);

        //NOTE bannerList 是为了解决articles.subList带来的多线程安全问题.
        if (data.get(0).banner != null && data.get(0).banner.size() > 0){
            ArrayList<ArticleModel> bannerList = new ArrayList<>();
            for (int i = 0; i < 3; i++){
                bannerList.add(data.get(0).banner.get(i));
            }
            //banner
            mBanner.setPages(new CBViewHolderCreator<BannerHolderView>() {
                @Override
                public BannerHolderView createHolder() {
                    return new BannerHolderView();
                }
            }, bannerList);
        }

        mArticleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String error) {
        mSwipeLayout.setRefreshing(false);
        mIsLoading = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startTurning(3500);

    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopTurning();
    }

}
