package com.askdog.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.ChannelBean;
import com.askdog.android.utils.transformations.glide.CropSquareTransformation;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wyong on 2016/8/8.
 */
public class ChannelViewAdapter extends RecyclerView.Adapter<ChannelViewAdapter.ViewHolder> {


    private final List<ChannelBean.ChannelResult> dataList;
    private final IOnClickListener mListener;
    private Context mContext;

    public ChannelViewAdapter(Context context, List<ChannelBean.ChannelResult> list, IOnClickListener listener) {
        mContext = context;
        dataList = list;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_channel_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                mListener.onItemViewClickListener(pos);
            }
        });
        ChannelBean.ChannelResult bean = dataList.get(position);
        holder.bindData(bean);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<ChannelBean.ChannelResult> getDataList() {
        return dataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.channel_item_thumb)
        ImageView thumb;
        @Bind(R.id.channel_item_name)
        TextView name;
        @Bind(R.id.channel_order_no)
        TextView order_no;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ChannelBean.ChannelResult result) {
            Glide.with(mContext)
                    .load(result.thumbnail)
                    .placeholder(R.drawable.channel_default_diagram)
                    .error(R.drawable.channel_default_diagram)
                    .bitmapTransform(new CropSquareTransformation(mContext))
                    .centerCrop()
                    .into(thumb);
            name.setText(result.name);
            order_no.setText("订阅数 " + result.subscriber_count);
        }
    }
}
