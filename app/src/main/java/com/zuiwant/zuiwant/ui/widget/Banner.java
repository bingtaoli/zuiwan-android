package com.zuiwant.zuiwant.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.model.ArticleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 16/4/26.
 */
public class Banner extends FrameLayout implements View.OnClickListener {
    private List<ArticleModel> topRecommendArticles;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    private List<View> views;
    private Context context;
    private ViewPager vp;
    private boolean isAutoPlay;
    private int currentItem;
    private int delayTime;
    private LinearLayout ll_dot;
    private List<ImageView> iv_dots;
    private Handler handler = new Handler();
    private OnItemClickListener mItemClickListener;

    public Banner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        this.context = context;
        this.topRecommendArticles = new ArrayList<>();
        initView();
    }

    private void initView() {
        views = new ArrayList<View>();
        iv_dots = new ArrayList<ImageView>();
        delayTime = 4000;
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context) {
        this(context, null);
    }

    public void setTopEntities(List<ArticleModel> topEntities) {
        this.topRecommendArticles = topEntities;
        reset();
    }

    private void reset() {
        views.clear();
        initUI();
    }

    private void initUI() {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_layout, this, true);
        vp = (ViewPager) view.findViewById(R.id.vp);
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        ll_dot.removeAllViews();

        int len = topRecommendArticles.size();
        for (int i = 0; i < len; i++) {
            ImageView iv_dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            ll_dot.addView(iv_dot, params);
            iv_dots.add(iv_dot);
            initIvDots();
        }

        for (int i = 0; i <= len + 1; i++) {
            View fm = LayoutInflater.from(context).inflate(R.layout.banner_content_layout, null);
            ImageView iv = (ImageView) fm.findViewById(R.id.iv_title);
            TextView tv_title = (TextView) fm.findViewById(R.id.tv_title);
            LinearLayout background = (LinearLayout)fm.findViewById(R.id.background_transparent);
            background.setAlpha((float)0.8);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (i == 0) {
                mImageLoader.displayImage(topRecommendArticles.get(len - 1).articleImg, iv, options);
                tv_title.setText(topRecommendArticles.get(len - 1).articleTitle);
            } else if (i == len + 1) {
                mImageLoader.displayImage(topRecommendArticles.get(0).articleImg, iv, options);
                tv_title.setText(topRecommendArticles.get(0).articleTitle);
            } else {
                mImageLoader.displayImage(topRecommendArticles.get(i - 1).articleImg, iv, options);
                tv_title.setText(topRecommendArticles.get(i - 1).articleTitle);
            }
            fm.setOnClickListener(this);
            views.add(fm);
        }
        vp.setAdapter(new MyPagerAdapter());
        vp.setFocusable(true);
        vp.setCurrentItem(0);
        currentItem = 0;
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        startPlay();
    }

    private void startPlay() {
        isAutoPlay = true;
        handler.postDelayed(task, delayTime);
    }

    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            currentItem = vp.getCurrentItem();
            if (currentItem == topRecommendArticles.size() - 1){
                //到头了,回到开始
                currentItem = 0;
            } else {
                currentItem++;
            }
            vp.setCurrentItem(currentItem);
            handler.postDelayed(task, delayTime);
        }
    };

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * Called when the scroll state changes
         * @param arg0
         */
        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                //Indicates that the pager is currently being dragged by the user
                case ViewPager.SCROLL_STATE_DRAGGING:
                    //先取消
                    handler.removeCallbacks(task);
                    handler.postDelayed(task, delayTime);
                    break;
                default:
                    break;
            }
        }

        /**
         * This method will be invoked when the current page is scrolled,
         * either as part of a programmatically initiated smooth scroll or a user initiated touch scroll.
         * @param arg0
         * @param arg1
         * @param arg2
         */
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        /**
         * This method will be invoked when a new page becomes selected.
         * @param arg0
         */
        @Override
        public void onPageSelected(int arg0) {
            Log.d("lee on page Selected", "" + arg0);
            for (int i = 0; i < iv_dots.size(); i++) {
                if (i == arg0) {
                    iv_dots.get(i).setImageResource(R.drawable.dot_focus);
                } else {
                    iv_dots.get(i).setImageResource(R.drawable.dot_blur);
                }
            }
        }
    }

    public void initIvDots(){
        if (iv_dots.size() == 0){
            return;
        }
        iv_dots.get(0).setImageResource(R.drawable.dot_focus);
        for (int i = 1; i < iv_dots.size(); i++){
            iv_dots.get(i).setImageResource(R.drawable.dot_blur);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
       // public void click(View v, Latest.TopStoriesEntity entity);
    }

    @Override
    public void onClick(View v) {
        //
    }
}
