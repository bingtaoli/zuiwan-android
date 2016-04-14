package com.zuiwant.zuiwant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.model.TopicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libingtao on 16/4/13.
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_topic, viewGroup, false);
        return new TopicViewHolder(v);
    }

    public void onBindViewHolder(TopicViewHolder viewHolder, int i) {
        final TopicModel topic = topics.get(i);
        viewHolder.topicName.setText(topic.topicName);
        viewHolder.articleNum.setText(String.valueOf(topic.articleNum));
        viewHolder.topicIntro.setText(topic.topicIntro);
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class TopicViewHolder extends BaseViewHolder {

        public TextView topicName;
        public TextView articleNum;
        public TextView topicIntro;

        public TopicViewHolder(View itemView) {
            super(itemView);
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
