package com.askdog.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.ChannelBean;
import com.askdog.android.model.ChannelDetailItemBean;
import com.askdog.android.utils.transformations.glide.CropSquareTransformation;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/20.
 */
public class MyOwnedChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private final Context mContext;
    private IOnClickListener mListener;

    private final List<ChannelBean.ChannelResult> mItemDatas;

    public MyOwnedChannelAdapter(Context context, List<ChannelBean.ChannelResult> data, IOnClickListener listener) {
        this.mItemDatas = data;
        this.mListener = listener;
        this.mContext = context;
    }

    public List<ChannelBean.ChannelResult> getDataList() {
        return mItemDatas;
    }

    public void addDataList(List<ChannelBean.ChannelResult> ls) {
        mItemDatas.addAll(ls);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_channel_item, parent, false);
            return new ItemViewHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.create_channel_footer_item, parent, false);
            return new FooterViewHolder(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            ChannelBean.ChannelResult bean = mItemDatas.get(position);
            itemViewHolder.bindData(bean);
            if (mListener == null) return;
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemViewClickListener(holder.getLayoutPosition());
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            if (mListener == null) return;
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemViewClickListener(-1);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        int itemCount = mItemDatas.size() + 1;

        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        boolean flg = (position == getItemCount() - 1);
        return flg;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout delete_area;
        ImageView thumb;

        TextView name;

        TextView order_no;

        public ItemViewHolder(View itemView) {
            super(itemView);
            thumb = (ImageView) itemView.findViewById(R.id.channel_item_thumb);
            name = (TextView) itemView.findViewById(R.id.channel_item_name);
            order_no = (TextView) itemView.findViewById(R.id.channel_order_no);
            delete_area = (LinearLayout) itemView.findViewById(R.id.my_channel_delete_area);
            delete_area.setVisibility(View.VISIBLE);
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
            delete_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemDeleteClickListener(getLayoutPosition());
                }
            });
        }
    }
}

class FooterViewHolder extends RecyclerView.ViewHolder {

    public FooterViewHolder(View itemView) {
        super(itemView);
    }
}

