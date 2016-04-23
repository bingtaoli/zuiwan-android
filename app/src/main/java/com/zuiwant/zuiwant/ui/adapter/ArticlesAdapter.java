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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 16/4/17.
 */
public class ArticlesAdapter extends BaseRecycleAdapter {

    private List<ArticleModel> articles = new ArrayList<>();

    public ArticlesAdapter(Context context){
        super(context);
    }

    public ArticlesAdapter(Context context, List<ArticleModel> articles){
        super(context);
        this.articles = articles;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ArticleViewHolder(inflater.inflate(R.layout.item_article, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        /**
         * NOTE
         * 不能把参数设置成TopicViewHolder,那样不能Override
         */
        ArticleViewHolder articleViewHolder = (ArticleViewHolder) viewHolder;
        final ArticleModel article = articles.get(i);
        articleViewHolder.articleMediaName.setText(article.articleMediaName);
        articleViewHolder.articleTitle.setText(article.articleTitle);
        if (article.articleImg != null && article.articleImg.length() > 0){
            articleViewHolder.ivCover.setImageURI(Uri.parse(article.articleImg));
        }
    }

    /**
     * 忘记重写该函数,导致notifyDataSetChanged没有办法刷新
     * @return
     */
    @Override
    public int getItemCount() {
        return articles.size();
    }


    public class ArticleViewHolder extends BaseViewHolder {
        SimpleDraweeView ivCover;
        public TextView articleMediaName;
        public TextView articleTitle;
        public TextView articleTopicName;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ivCover = (SimpleDraweeView) itemView.findViewById(R.id.iv_cover);
            articleMediaName = (TextView) itemView.findViewById(R.id.article_media);
            articleTitle = (TextView) itemView.findViewById(R.id.article_title);
            articleTopicName = (TextView) itemView.findViewById(R.id.article_topic);
        }
    }

    public void insertAtBack(ArrayList<ArticleModel> data, boolean merge) {
        if (merge)
            articles.addAll(data);
        else
            articles = data;
        //通知内容view已经更新
        notifyDataSetChanged();
    }
}
