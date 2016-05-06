package com.zuiwant.zuiwant;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zuiwant.zuiwant.ui.fragment.AccountFragment;
import com.zuiwant.zuiwant.ui.fragment.MediasFragment;
import com.zuiwant.zuiwant.ui.fragment.RecommendFragment;
import com.zuiwant.zuiwant.ui.fragment.TopicsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {

    private FragmentTabHost mTabHost;
    private LayoutInflater mLayoutInflater;
    private List<LinearLayout> mTabIndicators = new ArrayList<LinearLayout>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化Fresco,很重要
        Fresco.initialize(MainActivity.this);

        initTabHost();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initTabHost() {
        mLayoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);
        mTabHost.getTabWidget().setDividerDrawable(null);

        TabHost.TabSpec[] tabSpecs = new TabHost.TabSpec[4];
        String[] texts = new String[4];
        LinearLayout[] tabviews = new LinearLayout[4];

        Bundle bundle = new Bundle();
        texts[0] = getString(R.string.title_activity_main_recommend);
        LinearLayout tab1 = getTabView(R.layout.item_tab_recommend);
        Typeface font = Typeface.createFromAsset(getAssets(), "fontello.ttf");
        TextView recommend =  (TextView)tab1.findViewById(R.id.recommend_icon);
        recommend.setTypeface(font);
        recommend.setText(R.string.zw_font_recommend);
        tabviews[0] = tab1;
        tabSpecs[0] = mTabHost.newTabSpec(texts[0]).setIndicator(tabviews[0]);
        mTabHost.addTab(tabSpecs[0], RecommendFragment.class, bundle);
        mTabIndicators.add(tabviews[0]);

        texts[1] = getString(R.string.title_activity_main_topic);
        LinearLayout tab2 = getTabView(R.layout.item_tab_recommend);
        TextView topic =  (TextView)tab2.findViewById(R.id.recommend_icon);
        topic.setTypeface(font);
        topic.setText(R.string.zw_font_recommend);
        tabviews[1] = tab2;
        tabSpecs[1] = mTabHost.newTabSpec(texts[1]).setIndicator(tabviews[1]);
        mTabHost.addTab(tabSpecs[1], TopicsFragment.class, null);
        mTabIndicators.add(tabviews[1]);

        texts[2] = getString(R.string.title_activity_main_media);
        LinearLayout tab3 = getTabView(R.layout.item_tab_recommend);
        TextView media =  (TextView)tab3.findViewById(R.id.recommend_icon);
        media.setTypeface(font);
        media.setText(R.string.zw_font_recommend);
        tabviews[2] = tab3;
        tabSpecs[2] = mTabHost.newTabSpec(texts[2]).setIndicator(tabviews[2]);
        mTabHost.addTab(tabSpecs[2], MediasFragment.class, null);
        mTabIndicators.add(tabviews[2]);

        texts[3] = getString(R.string.title_activity_main_account);
        LinearLayout tab4 = getTabView(R.layout.item_tab_recommend);
        TextView account =  (TextView)tab4.findViewById(R.id.recommend_icon);
        account.setTypeface(font);
        account.setText(R.string.zw_font_recommend);
        tabviews[3] = tab4;
        tabSpecs[3] = mTabHost.newTabSpec(texts[3]).setIndicator(tabviews[3]);
        mTabHost.addTab(tabSpecs[3], AccountFragment.class, null);
        mTabIndicators.add(tabviews[3]);
        mTabHost.setOnTabChangedListener(this);
        resetTabs();
        LinearLayout tabview =  mTabIndicators.get(0);
        if (tabview != null){
            for (int j = 0; j < tabview.getChildCount(); j++){
                View view = tabview.getChildAt(j);
                if (view instanceof TextView){
                    TextView textView = (TextView) view;
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        }
    }

    private LinearLayout getTabView(int layoutId) {
        LinearLayout tab = (LinearLayout) mLayoutInflater.inflate(layoutId, null);
        return tab;
    }

    @Override
    public void onTabChanged(String tabId) {

        resetTabs();

        LinearLayout tabview = (LinearLayout) mTabHost.getCurrentTabView();
        if (tabview != null){
            for (int i = 0; i < tabview.getChildCount(); i++){
                View view = tabview.getChildAt(i);
                if (view instanceof TextView){
                    TextView textView = (TextView) view;
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        }
    }

    /**
     * 重置其他的TabIndicator的颜色
     */
    private void resetTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            LinearLayout tabview = mTabIndicators.get(i);
            if (tabview != null){
                for (int j = 0; j < tabview.getChildCount(); j++){
                    View view = tabview.getChildAt(j);
                    if (view instanceof TextView){
                        TextView textView = (TextView) view;
                        textView.setTextColor(getResources().getColor(R.color.gray_80));
                    }
                }
            }
        }
    }
}
