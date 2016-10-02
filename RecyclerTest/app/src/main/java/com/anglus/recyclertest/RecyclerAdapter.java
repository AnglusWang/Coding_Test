package com.anglus.recyclertest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anglus on 2016/10/2.
 */

public class RecyclerAdapter
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<String> mData;

    public RecyclerAdapter(List<String> data) {
        mData = data;
    }

    public AdapterView.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(
            OnItemClickLister itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface OnItemClickLister {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
            mTextView.setOnClickListener(this);
        }

        // 通过接口回调来实现 RecycleView 的点击事件
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v.getPosition);
            }
        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 将布局转换为 View 并传递给 RecycleView 封装好的 ViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.rc_item, parent, false
        );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        // 建立起 ViewHolder 中视图与数据的关联
        holder.mTextView.setText(mData.get(position) + position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
