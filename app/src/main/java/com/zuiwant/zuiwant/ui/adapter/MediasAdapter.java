package com.zuiwant.zuiwant.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.model.MediaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 16/4/13.
 */
public class MediasAdapter extends BaseRecycleAdapter {

    private List<MediaModel> medias = new ArrayList<>();

    public MediasAdapter(Context context){
        super(context);
    }

    public MediasAdapter(Context context, List<MediaModel> medias) {
        super(context);
        this.medias = medias;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_media, viewGroup, false);
        return new MediaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        MediaViewHolder mediaViewHolder = (MediaViewHolder) viewHolder;
        final MediaModel media = medias.get(i);
        mediaViewHolder.mediaName.setText(media.mediaName);
        mediaViewHolder.articleNum.setText(String.valueOf(media.articleNum));
        mediaViewHolder.mediaIntro.setText(media.mediaIntro);
        if (media.mediaAvatar != null && media.mediaAvatar.length() > 0){
            mediaViewHolder.ivCover.setImageURI(Uri.parse(media.mediaAvatar));
        }
    }

    @Override
    public int getItemCount() {
        return medias.size();
    }

    public class MediaViewHolder extends BaseViewHolder {

        public TextView mediaName;
        public TextView articleNum;
        public TextView mediaIntro;
        SimpleDraweeView ivCover;

        public MediaViewHolder(View itemView) {
            super(itemView);
            mediaName = (TextView) itemView.findViewById(R.id.media_name);
            mediaIntro = (TextView) itemView.findViewById(R.id.media_intro);
            articleNum = (TextView) itemView.findViewById(R.id.txt_articles);
            ivCover = (SimpleDraweeView) itemView.findViewById(R.id.avatar);
        }
    }

    public void insertAtBack(ArrayList<MediaModel> data, boolean merge) {
        if (merge)
            medias.addAll(data);
        else
            medias = data;
        //通知内容view已经更新
        notifyDataSetChanged();
    }

}
