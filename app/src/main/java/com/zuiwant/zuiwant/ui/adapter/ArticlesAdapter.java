package com.zuiwant.zuiwant.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.model.ArticleModel;
import com.zuiwant.zuiwant.ui.widget.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 16/4/17.
 */
public class ArticlesAdapter extends BaseRecycleAdapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    // TODO 也许adapter的数据很容易会被销毁,然后就会导致首页只有很后面的文章显示
    private List<ArticleModel> articles;
    public Banner banner = null;
    private boolean hasGotTopArticles = false;

    public ArticlesAdapter(Context context, List<ArticleModel> articlesList){
        super(context);
        this.articles = articlesList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ITEM){
            return new ArticleViewHolder(inflater.inflate(R.layout.item_article, viewGroup, false));
        } else if (viewType == TYPE_HEADER){
            View header = inflater.inflate(R.layout.banner, viewGroup, false);
            banner = (Banner) header.findViewById(R.id.banner);
            return new VHHeader(header);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ArticleViewHolder){
            int realPosition = i - 1;
            /**
             * NOTE
             * 不能把参数设置成TopicViewHolder,那样不能Override
             */
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) viewHolder;
            final ArticleModel article = articles.get(realPosition);
            //articleViewHolder.articleMediaName.setText(article.articleMediaName);
            articleViewHolder.articleTitle.setText(article.articleTitle);
            if (article.articleImg != null && article.articleImg.length() > 0){
                articleViewHolder.ivCover.setImageURI(Uri.parse(article.articleImg));
            }
        } else if (viewHolder instanceof VHHeader){
            if (!hasGotTopArticles  && articles.size() > 2){
                banner.setTopEntities(articles.subList(0, 3));
                hasGotTopArticles = true;
            }
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }
    }

    /**
     * 忘记重写该函数,导致notifyDataSetChanged没有办法刷新
     * @return
     */
    @Override
    public int getItemCount() {
        return articles.size() + 1;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    public class ArticleViewHolder extends BaseViewHolder {
        SimpleDraweeView ivCover;
        public TextView articleMediaName;
        public TextView articleTitle;
        public TextView articleTopicName;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ivCover = (SimpleDraweeView) itemView.findViewById(R.id.iv_cover);
            //articleMediaName = (TextView) itemView.findViewById(R.id.article_media);
            articleTitle = (TextView) itemView.findViewById(R.id.article_title);
            articleTopicName = (TextView) itemView.findViewById(R.id.article_topic);
        }
    }

    class VHHeader extends RecyclerView.ViewHolder {

        public VHHeader(View itemView) {
            super(itemView);
        }

    }

    public void insertAtBack(ArrayList<ArticleModel> data, boolean merge) {
        if (merge){
            articles.addAll(data);
        }
        else {
            articles = data;
        }
        //通知内容view已经更新
        notifyDataSetChanged();
    }
}
