package com.zuiwant.zuiwant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.api.HttpRequestHandler;
import com.zuiwant.zuiwant.api.ZWManager;
import com.zuiwant.zuiwant.model.ArticleContentModel;
import com.zuiwant.zuiwant.model.ArticleModel;
import com.zuiwant.zuiwant.widget.RichTextView;

import java.util.ArrayList;

/**
 * Created by matthew on 16/4/23.
 */
public class ArticleActivity extends AppCompatActivity implements HttpRequestHandler<ArrayList<ArticleContentModel>> {

    public ArticleModel article;
    public int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        initIntentParam(getIntent());

        /**
         * no need for article fragment, use article activity to show ui
         */
        setContentView(R.layout.article_content);

        RichTextView contentTextView = (RichTextView) findViewById(R.id.text_content);

        String content = "<p>hello, world</p>" +
                "<img src=\"http://zuiwant.com/zuiwan-backend/public/upload/img/1461323089384137.jpg\">";
        contentTextView.setRichText(content);

        requestArticleContent();
    }

    public void initIntentParam(Intent intent){
        article = (ArticleModel) intent.getSerializableExtra("article");
        if (article != null){
            articleId = article.id;
        } else {
            articleId = 0;
        }
    }

    public void requestArticleContent(){
        if (articleId != 0){
            ZWManager.getOneArticle(this, articleId, false, this);
        } else {
            Log.d("lee", "wrong article id");
        }
    }

    @Override
    public void onSuccess(ArrayList<ArticleContentModel> data) {
        if (data.size() == 1){
            RichTextView contentTextView = (RichTextView) findViewById(R.id.text_content);
            String content =  data.get(0).articleContent;
            contentTextView.setRichText(content);
        } else {
            Log.d("lee", "get article content fail");
        }
    }

    @Override
    public void onSuccess(ArrayList<ArticleContentModel> data, int totalPages, int currentPage) {

    }

    @Override
    public void onFailure(String error) {

    }
}
