package com.zuiwant.zuiwant.widget;

/**
 * Created by matthew on 16/4/19.
 */
import android.app.Application;
import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zuiwant.zuiwant.utils.AsyncImageGetter;

import java.util.ArrayList;

/**
 * Created by yw on 2015/5/10.
 */
public class RichTextView extends TextView {

    public RichTextView(Context context) {
        super(context);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setRichText(String text) {

        Spanned spanned = Html.fromHtml(text, new AsyncImageGetter(getContext(), this), null);
        SpannableStringBuilder htmlSpannable;
        if (spanned instanceof SpannableStringBuilder) {
            htmlSpannable = (SpannableStringBuilder) spanned;
        } else {
            htmlSpannable = new SpannableStringBuilder(spanned);
        }

        ImageSpan[] spans = htmlSpannable.getSpans(0, htmlSpannable.length(), ImageSpan.class);
        final ArrayList<String> imageUrls = new ArrayList<String>();
        final ArrayList<String> imagePositions = new ArrayList<String>();
        for (ImageSpan span : spans) {
            final String imageUrl = span.getSource();
            final int start = htmlSpannable.getSpanStart(span);
            final int end = htmlSpannable.getSpanEnd(span);
            imagePositions.add(start + "/" + end);
            imageUrls.add(imageUrl);
        }

        for(ImageSpan span : spans){
            final int start = htmlSpannable.getSpanStart(span);
            final int end   = htmlSpannable.getSpanEnd(span);

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //后续加上图片点击出现所有图片滑动展示
                    //PhotoViewActivity.launch(getContext(), imagePositions.indexOf(start + "/" + end), imageUrls);
                }
            };

            ClickableSpan[] clickSpans = htmlSpannable.getSpans(start, end, ClickableSpan.class);
            if(clickSpans != null && clickSpans.length != 0) {

                for(ClickableSpan c_span : clickSpans) {
                    htmlSpannable.removeSpan(c_span);
                }
            }

            htmlSpannable.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        super.setText(spanned);
        setMovementMethod(LinkMovementMethod.getInstance());
    }
}
