package com.zuiwant.zuiwant.ui.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.TextHttpResponseHandler;
import com.zuiwant.zuiwant.R;

import com.zuiwant.zuiwant.model.TopicModel;
import com.zuiwant.zuiwant.ui.adapter.BaseRecycleAdapter;
import com.zuiwant.zuiwant.ui.adapter.TopicsAdapter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by libingtao on 16/4/13.
 */
public class TopicFragment extends BaseFragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TopicsAdapter mTopicAdapter;
    SwipeRefreshLayout mSwipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_topics, container, false);

        final Context context = getActivity();

        ArrayList<TopicModel> testList = new ArrayList<>();
        testList.add(new TopicModel(true));
        testList.add(new TopicModel(true));
        testList.add(new TopicModel(true));
        testList.add(new TopicModel(true));

        //TODO 这两步可以封在一个initView函数中,类似ListenerRain项目做的一样
        mSwipeLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.list_topics);

        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        //TODO 可以封在setViewStatus函数中
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTopicAdapter = new TopicsAdapter(context, testList);
        mRecyclerView.setAdapter(mTopicAdapter);

        mTopicAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //打开一个新的activity
                //暂时只记录log
                Log.d("lee", "one item is clicked");
            }
        });

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTopicList(getActivity());
                Log.d("lee", "on refreshing");
            }
        });

        return layout;
    }

    public ArrayList getTopicList(Context ctx) {
        ArrayList<TopicModel> list = new ArrayList<>();
        AsyncHttpClient client =  new AsyncHttpClient();
        client.get("http://zuiwant.com/zuiwan-backend/index.php/topic/get_topic", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("lee", "fail get topic list");
                //TODO remind failure
                //SafeHandler.onFailure(handler, V2EXErrorType.errorMessage(ctx, V2EXErrorType.ErrorGetTopicListFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, final String responseBody) {
                new AsyncTask<Void, Void, ArrayList>() {
                    @Override
                    protected ArrayList doInBackground(Void... params) {
                        ArrayList<TopicModel> list = new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(responseBody);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject obj =  array.getJSONObject(i);
                                TopicModel topic = new TopicModel();
                                topic.topicIntro = obj.optString("topic_intro");
                                topic.topicName = obj.optString("topic_name");
                                topic.articleNum = obj.optInt("article_count");
                                list.add(topic);
                            }
                            Log.d("lee", "doing get topic list");
                            Log.d("lee", "result is " + responseBody);
                            return list;
                        } catch (Exception e) {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(ArrayList topics) {
                        Log.d("lee", "finished get topic list");
                    }
                }.execute();
            }
        });

        return list;
    }

}
