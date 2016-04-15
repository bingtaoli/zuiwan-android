package com.zuiwant.zuiwant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private List<TopicModel> topics = new ArrayList<>();

    public TopicsAdapter(Context context){
        super(context);
    }

    public TopicsAdapter(Context context, List<TopicModel> topics) {
        super(context);
        this.topics = topics;
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
        Log.d("lee", "topics adapter set topic name" + topic.topicName);
        topicViewHolder.topicName.setText(topic.topicName);
        Log.d("lee", "topics adapter set topic article count" + topic.articleNum);
        topicViewHolder.articleNum.setText(String.valueOf(topic.articleNum));
        topicViewHolder.topicIntro.setText(topic.topicIntro);
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

    public void insertAtBack(ArrayList<TopicModel> data, boolean merge) {
        if (merge)
            topics.addAll(data);
        else
            topics = data;
        //通知内容view已经更新
        notifyDataSetChanged();
    }

}
