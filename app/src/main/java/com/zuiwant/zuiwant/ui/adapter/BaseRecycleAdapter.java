package com.zuiwant.zuiwant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by libingtao on 16/4/14.
 */
public class BaseRecycleAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context context;
    protected LayoutInflater inflater;
    protected OnItemClickListener onItemClickListener;

    public BaseRecycleAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * 记得重写
     * @return
     */
    @Override
    public  int getItemCount() {
        return 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    /**
     * BaseRecycleAdapter中定义BaseViewHolder
     * 因为两者总是一起使用
     * 实现View.OnClickListener以便监听click
     */
    protected class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public BaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /**
             * onItemClickListener是属于RecycleAdapter
             * 很符合定制化需求
             */
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
