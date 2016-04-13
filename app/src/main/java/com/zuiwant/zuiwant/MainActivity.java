package com.zuiwant.zuiwant;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.zuiwant.zuiwant.ui.fragment.TopicFragment;
import com.zuiwant.zuiwant.ui.widget.ChangeColorIconWithText;
import com.zuiwant.zuiwant.ui.fragment.ViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {

    private FragmentTabHost mTabHost;
    private LayoutInflater mLayoutInflater;
    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabHost();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initTabHost() {
        mLayoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);
        mTabHost.getTabWidget().setDividerDrawable(null);

        TabHost.TabSpec[] tabSpecs = new TabHost.TabSpec[3];
        String[] texts = new String[3];
        ChangeColorIconWithText[] tabviews = new ChangeColorIconWithText[3];

        Bundle bundle = new Bundle();
        bundle.putInt("type", ViewPagerFragment.TypeViewPager_Aggregation);
        texts[0] = getString(R.string.title_activity_main_recommend);
        tabviews[0] = getTabView(R.layout.item_tab_recommend);
        tabSpecs[0] = mTabHost.newTabSpec(texts[0]).setIndicator(tabviews[0]);
        mTabHost.addTab(tabSpecs[0], ViewPagerFragment.class, bundle);
        mTabIndicators.add(tabviews[0]);

        texts[1] = getString(R.string.title_activity_main_topic);
        tabviews[1] = getTabView(R.layout.item_tab_topic);
        tabSpecs[1] = mTabHost.newTabSpec(texts[1]).setIndicator(tabviews[1]);
        mTabHost.addTab(tabSpecs[1], TopicFragment.class, null);
        mTabIndicators.add(tabviews[1]);

        texts[2] = getString(R.string.title_activity_main_media);
        tabviews[2] = getTabView(R.layout.item_tab_media);
        tabSpecs[2] = mTabHost.newTabSpec(texts[2]).setIndicator(tabviews[2]);
        mTabHost.addTab(tabSpecs[2], ViewPagerFragment.class, null);
        mTabIndicators.add(tabviews[2]);

        mTabHost.setOnTabChangedListener(this);
        tabviews[0].setIconAlpha(1.0f);
        setTitle(texts[0]);
    }

    private ChangeColorIconWithText getTabView(int layoutId) {
        ChangeColorIconWithText tab = (ChangeColorIconWithText) mLayoutInflater.inflate(layoutId, null);
        return tab;
    }

    @Override
    public void onTabChanged(String tabId) {

        resetOtherTabs();

        ChangeColorIconWithText tabview = (ChangeColorIconWithText) mTabHost.getCurrentTabView();
        if (tabview != null)
            tabview.setIconAlpha(1.0f);
    }

    /**
     * 重置其他的TabIndicator的颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }
}
