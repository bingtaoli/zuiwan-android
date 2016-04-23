package com.zuiwant.zuiwant.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.ui.fragment.ArticleFragment;

/**
 * Created by matthew on 16/4/23.
 */
public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ArticleFragment fragment = new ArticleFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
    }

}
