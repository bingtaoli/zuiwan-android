package com.zuiwant.zuiwant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.model.TopicModel;
import com.zuiwant.zuiwant.utils.OnScrollToBottomListener;

import java.util.ArrayList;

/**
 * Created by libingtao on 16/4/13.
 */
public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder> {

    Context mContext;
    OnScrollToBottomListener mListener;
    ArrayList<TopicModel> mTopics = new ArrayList<TopicModel>();

    public TopicsAdapter (Context context, ArrayList<TopicModel> topics){
        mContext = context;
        mTopics = topics;
    }

    public TopicsAdapter(Context context, OnScrollToBottomListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_topic, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final TopicModel topic = mTopics.get(i);

        viewHolder.topicName.setText(topic.topicName);
        viewHolder.articleNum.setText(String.valueOf(topic.articleNum));
        viewHolder.topicIntro.setText(topic.topicIntro);

    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    public void insertAtBack(ArrayList<TopicModel> data, boolean merge) {
        if (merge)
            mTopics.addAll(data);
        else
            mTopics = data;
        notifyDataSetChanged();
    }

    public void update(ArrayList<TopicModel> data, boolean merge) {
        if (merge && mTopics.size() > 0) {
            for (int i = 0; i < mTopics.size(); i++) {
                TopicModel obj = mTopics.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).topicId == obj.topicId) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                data.add(obj);
            }
        }
        mTopics = data;

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 每个item view中只有3个显示
         * 背景图后续补上
         */
        public TextView topicName;
        public TextView articleNum;
        public TextView topicIntro;

        public ViewHolder(View view) {
            super(view);
            topicName = (TextView) view.findViewById(R.id.topic_name);
            topicIntro = (TextView) view.findViewById(R.id.topicIntro);
            articleNum = (TextView) view.findViewById(R.id.txt_articles);
        }

    }

}
