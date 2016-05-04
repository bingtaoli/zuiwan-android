package com.zuiwant.zuiwant.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zuiwant.zuiwant.R;

/**
 * Created by matthew on 16/5/4.
 */
public class TitleBar extends LinearLayout {
    private Context context;
    private LinearLayout rootLayout;
    private TextView tvTitle;
    private ImageView ivBack;


    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.title_bar, this);
        rootLayout = (LinearLayout) findViewById(R.id.root);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    activity.onBackPressed();
                }
            }
        });
        setColor(true);

    }

    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }


    public void setColor(boolean transparent) {
        if (transparent) {
            rootLayout.setBackgroundColor(getResources().getColor(R.color.gray_80));
        } else {
            rootLayout.setBackgroundColor(getResources().getColor(R.color.gray));
        }

    }
}