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

import java.util.List;

/*
 * Created by matthew on 16/5/7.
 */
public class TopicArticlesAdapter extends BaseRecycleAdapter {

    private List<ArticleModel> articles;

    public TopicArticlesAdapter(Context context, List<ArticleModel> topicArticles) {
        super(context);
        articles = topicArticles;
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

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ArticleViewHolder(inflater.inflate(R.layout.item_article, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ArticleViewHolder articleViewHolder = (ArticleViewHolder) viewHolder;
        final ArticleModel article = articles.get(i);
        articleViewHolder.articleTitle.setText(article.articleTitle);
        if (article.articleImg != null && article.articleImg.length() > 0){
            articleViewHolder.ivCover.setImageURI(Uri.parse(article.articleImg));
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
