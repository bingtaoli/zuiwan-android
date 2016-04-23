package com.zuiwant.zuiwant.ui.fragment;

import com.zuiwant.zuiwant.R;
import com.zuiwant.zuiwant.model.ArticleModel;
import com.zuiwant.zuiwant.widget.RichTextView;

/**
 * Created by matthew on 16/4/19.
 */
public class ArticleFragment extends BaseFragment {

    ArticleModel mArticle;
    int mArticleId;

    @Override
    protected int setRootViewResId() {
        return R.layout.article_content;
    }

    @Override
    public void setViewStatus(){
        RichTextView contentTextView = (RichTextView) rootView.findViewById(R.id.text_content);
        String content = "<p>hello, world</p>" +
                "<img src=\"http://zuiwant.com/zuiwan-backend/public/upload/img/1461323089384137.jpg\">";
        contentTextView.setRichText(content);
    }

}
