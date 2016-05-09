package com.zuiwant.zuiwant.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matthew on 16/4/23.
 */
public class ArticleContentModel extends ZuiwantModel  {

    public String articleAuthor;
    public String articleContent;
    public String articleImg;
    public String articleTitle;
    public String createTime;

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        articleAuthor = jsonObject.getString("article_author");
        articleContent = jsonObject.getString("article_content");
        articleImg = jsonObject.getString("article_img");
        articleTitle = jsonObject.getString("article_title");
        createTime = jsonObject.getString("create_time");
    }

    public static String formatContent(String content){
        // DONE 正则把图片src补上zuiwant.com
        String replaceHttp = "http://zuiwant.com";
        content = repairContent(content, replaceHttp);
        return content;
    }

    public static String getHtml(String content){
        String head = "<head>" +
                "<style type=\"text/css\">" +
                "img {" +
                    "max-width: 99% !important;" +
                    "height: auto!important;" +
                "}" +
                "p {" +
                    "word-break: break-all !important;" +
                    "max-width: 100% !important;" +
                "}" +
                ".article .article-content .content img {" +
                    "max-width: 99% !important;" +
                    "height: auto!important;" +
                "}" +
                "</style>" +
                "</head>";
        String body = "<body>";
        String bodyEnd = "</body>";
        String html = head + body + content + bodyEnd;
        return html;
    }

    /**
     * 将img标签中的src进行二次包装
     * @param content 内容
     * @param replaceHttp 需要在src中加入的域名
     * @return
     * 关键在于正则表达式：<img\\s*([^>]*)\\s*src=\\\"(.*?)\\\"\\s*([^>]*)>
     * 特别是 ([^>]*) 不能用.*代替，否则只会从<img匹配到字符串最后一个">"符号为止，如果每个src的内容不一样，就只会替换最后一个src
     *
     * http://it.5yun.com.cn/html/y2015/m03/112.html
     *
     */
    private static String repairContent(String content,String replaceHttp){
        /**
         * 其中\\会转换成\这一个字符
         * \\s为\s, 匹配任何空白字符
         */
        // DONE 微信图片会带有data-src,会导致bug,参见"校园最美手写大赛"那篇文章
        // \\s+表示一定要有空格,防止data-src
        String patternStr="<img\\s*([^>]*)\\s+src=\\\"(.*?)\\\"\\s*([^>]*)>";
        Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        String result = content;
        int i = 0;
        while(matcher.find()) {
            String src = matcher.group(2);
            Log.d("lee", "src is: " + src);
            String replaceSrc = "";
            if(src.lastIndexOf(".")>0){
                replaceSrc = src.substring(0,src.lastIndexOf(".")) + src.substring(src.lastIndexOf("."));
            }
            //Log.d("lee", "replaceSrc is: " + replaceSrc);
            if(!src.startsWith("http://")&&!src.startsWith("https://")){
                replaceSrc = replaceHttp + replaceSrc;
            }
            //Log.d("lee", "replaceSrc result is: " + replaceSrc);
            result = result.replaceAll(src, replaceSrc);
            i++;
        }
        Log.d("lee img count is ", "" + i);
        return result;
    }

}
