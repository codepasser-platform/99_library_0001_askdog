package com.askdog.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.askdog.android.model.ChannelDetailItemBean;

import java.util.List;

/**
 * Created by wyong on 2016/8/19.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private List<ChannelDetailItemBean.ChannelExpResult> mItemDatas;
    private boolean mWithHeader;
    private boolean mWithFooter;
    public BaseRecyclerViewAdapter(List<ChannelDetailItemBean.ChannelExpResult> data, boolean withHeader, boolean withFooter) {
        this.mItemDatas = data;
        mWithHeader = withHeader;
        mWithFooter = withFooter;
    }

    public List<ChannelDetailItemBean.ChannelExpResult> getmData() {
        return mItemDatas;
    }

    //region Get View
    protected abstract RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent);
    //endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            return getItemView(inflater, parent);
        } else if (viewType == TYPE_HEADER) {
            return getHeaderView(inflater, parent);
        } else if (viewType == TYPE_FOOTER) {
            return getFooterView(inflater, parent);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public int getItemCount() {
        int itemCount = mItemDatas.size();
        if (mWithHeader)
            itemCount++;
        if (mWithFooter)
            itemCount++;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (mWithFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    protected ChannelDetailItemBean.ChannelExpResult getItem(int position) {
        return mWithHeader ? mItemDatas.get(position - 1) : mItemDatas.get(position);
    }
}
