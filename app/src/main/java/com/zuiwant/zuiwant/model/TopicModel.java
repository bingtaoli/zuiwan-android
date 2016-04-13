package com.zuiwant.zuiwant.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by libingtao on 16/4/13.
 * 实现Parcelable接口,Parcelable是Android特有的功能，效率比实现Serializable接口高，像用于Intent数据传递也都支持，
 * 而且还可以用在进程间通信(IPC)，除了基本类型外，只有实现了Parcelable接口的类才能被放入Parcel中。
 */
public class TopicModel extends ZuiwantModel implements Parcelable {

    private static final long serialVersionUID = 2015050105L;

    public int topicId;
    public String topicName;
    public int articleNum;
    public String topicIntro;

    public TopicModel(){}

    public TopicModel(boolean test){
        if (test){
            topicId = 1;
            topicName = "测试";
            topicIntro = "测试专题";
            articleNum = 1;
        }
    }

    private TopicModel(Parcel in){
        int[] ints= new int[2];
        in.readIntArray(ints);
        topicId = ints[0];
        articleNum = ints[1];
        String[] strings = new String[2];
        topicName = strings[0];
        topicIntro = strings[1];
    }

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {

        topicId = jsonObject.getInt("id");
        articleNum = jsonObject.getInt("articleNum");
        topicName = jsonObject.getString("topicName");
        topicIntro = jsonObject.getString("topicIntro");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<TopicModel> CREATOR = new Creator<TopicModel>() {
        @Override
        public TopicModel createFromParcel(Parcel source) {
            return new TopicModel(source);
        }

        @Override
        public TopicModel[] newArray(int size) {
            return new TopicModel[size];
        }
    };

}
