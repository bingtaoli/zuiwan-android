package com.zuiwant.zuiwant.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.model.ArticleModel;
import com.zuiwant.zuiwant.model.TopicDetailModel;
import com.zuiwant.zuiwant.model.TopicModel;

import java.util.List;

/*
 * Created by matthew on 16/5/7.
 */
public class TopicArticlesAdapter extends BaseRecycleAdapter {

    private TopicDetailModel topicDetailModel  = null;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public TopicArticlesAdapter(Context context, TopicDetailModel topicDetailModel) {
        super(context);
        this.topicDetailModel = topicDetailModel;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ITEM){
            return new ArticleViewHolder(inflater.inflate(R.layout.topic_article_item, viewGroup, false));
        } else if (viewType == TYPE_HEADER){
            return new VHHeader(inflater.inflate(R.layout.topic_article_header, viewGroup, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (topicDetailModel.articles.size() == 0){
            return;
        }
        if (viewHolder instanceof ArticleViewHolder){
            int position = i - 1;
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) viewHolder;
            final ArticleModel article = topicDetailModel.articles.get(position);
            articleViewHolder.articleTitle.setText(article.articleTitle);
            if (article.articleImg != null && article.articleImg.length() > 0){
                articleViewHolder.ivCover.setImageURI(Uri.parse(article.articleImg));
            }
        } else if (viewHolder instanceof VHHeader){
            VHHeader headerViewHolder = (VHHeader) viewHolder;
            if (topicDetailModel != null){
                headerViewHolder.ivCover.setImageURI(Uri.parse(topicDetailModel.topicImg));
            }
        }
    }

    @Override
    public int getItemCount() {
        return topicDetailModel.articles.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)){
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public class ArticleViewHolder extends BaseViewHolder {
        SimpleDraweeView ivCover;
        public TextView articleTitle;
        public TextView articleTopicName;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ivCover = (SimpleDraweeView) itemView.findViewById(R.id.iv_cover);
            articleTitle = (TextView) itemView.findViewById(R.id.article_title);
            articleTopicName = (TextView) itemView.findViewById(R.id.article_topic);
        }
    }

    public class VHHeader extends BaseViewHolder {
        SimpleDraweeView ivCover;

        public VHHeader(View itemView) {
            super(itemView);
            ivCover = (SimpleDraweeView) itemView.findViewById(R.id.iv_cover);
        }

    }
}
