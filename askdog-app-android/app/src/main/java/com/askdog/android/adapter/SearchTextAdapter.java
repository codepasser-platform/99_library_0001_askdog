package com.askdog.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.askdog.android.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SearchTextAdapter extends RecyclerView.Adapter<SearchTextAdapter.ViewHolder> {


    private List<String> mDatas;
    private Context mContext;

    public SearchTextAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_search_text_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mDatas.get(position));
    }

    public List<String> getDatas() {
        return mDatas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.search_text_item);
        }
    }
}
