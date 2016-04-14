package com.zuiwant.zuiwant.api;

/**
 * Created by matthew on 16/4/14.
 * 任何和http请求有关的任务的结果处理
 */
public interface HttpRequestHandler<E> {
    public void onSuccess(E data);

    public void onSuccess(E data, int totalPages, int currentPage);

    public void onFailure(String error);
}
