package com.zuiwant.zuiwant.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.model.TopicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 16/4/13.
 */
public class TopicsAdapter extends BaseRecycleAdapter {

    private List<TopicModel> topics;

    public TopicsAdapter(Context context, List<TopicModel> topicsList){
        super(context);
        topics = topicsList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new TopicViewHolder(inflater.inflate(R.layout.item_topic, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        /**
         * NOTE
         * 不能把参数设置成TopicViewHolder,那样不能Override
         */
        TopicViewHolder topicViewHolder = (TopicViewHolder) viewHolder;
        final TopicModel topic = topics.get(i);
        topicViewHolder.topicName.setText(topic.topicName);
        topicViewHolder.articleNum.setText(String.valueOf(topic.articleNum));
        topicViewHolder.topicIntro.setText(topic.topicIntro);
        if (topic.topicImg != null && topic.topicImg.length() > 0){
            topicViewHolder.ivCover.setImageURI(Uri.parse(topic.topicImg));
        }
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class TopicViewHolder extends BaseViewHolder {

        SimpleDraweeView ivCover;
        public TextView topicName;
        public TextView articleNum;
        public TextView topicIntro;

        public TopicViewHolder(View itemView) {
            super(itemView);
            ivCover = (SimpleDraweeView) itemView.findViewById(R.id.iv_cover);
            topicName = (TextView) itemView.findViewById(R.id.topic_name);
            topicIntro = (TextView) itemView.findViewById(R.id.topicIntro);
            articleNum = (TextView) itemView.findViewById(R.id.txt_articles);
        }
    }
}
