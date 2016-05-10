package com.zuiwant.zuiwant.ui.widget;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.model.ArticleModel;

/*
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class BannerHolderView implements Holder<ArticleModel> {

    TextView tv_title;
    SimpleDraweeView iv;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        View view = LayoutInflater.from(context).inflate(R.layout.banner_content_layout, null);
        iv = (SimpleDraweeView) view.findViewById(R.id.iv_title);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void UpdateUI(Context context,int position, ArticleModel data) {
        if (data != null){
            iv.setImageURI(Uri.parse(data.articleImg));
            tv_title.setText(data.articleTitle);
        }
    }
}
