package com.askdog.android.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.model.ChannelDetailItemBean;
import com.askdog.android.model.ChannelDetailItemBean.ChannelExpResult;
import com.askdog.android.model.ChannelInfoBean;
import com.askdog.android.utils.DateUtils;
import com.askdog.android.utils.NLog;
import com.askdog.android.utils.transformations.glide.CropSquareTransformation;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyong on 2016/8/19.
 */
public class ChannelHomePageAdapter extends BaseRecyclerViewAdapter<ChannelDetailItemBean> {

    private final Context mContext;
    private ChannelInfoBean mHeaderData;
    private OnChannelHomeViewClickListener mListener;


    public ChannelHomePageAdapter(Context context, List<ChannelExpResult> data) {
        super(data, true, false);
        this.mContext = context;
    }

    public void setOnItemClickListener(OnChannelHomeViewClickListener click) {
        mListener = click;
    }

    public ChannelInfoBean getHeaderData() {
        return mHeaderData;
    }

    public void setHeaderData(ChannelInfoBean headerData) {
        this.mHeaderData = headerData;
        notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new ItemViewHolder(inflater.inflate(R.layout.activity_channel_home_list_item, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.activity_channel_home_list_header, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }
    //endregion

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final ChannelExpResult data = getItem(position);
            itemViewHolder.bindData(data);
            if (mListener == null) return;
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position - 1, data);
                }
            });
        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder mHeaderViewHolder = (HeaderViewHolder) holder;
            if (mHeaderData != null)
                mHeaderViewHolder.bindData(mHeaderData);
        } else if (holder instanceof FooterViewHolder) {

        }
    }

    public interface OnChannelHomeViewClickListener {
        void onItemClick(int position, ChannelExpResult data);

        void onSubscribeViewClick(int type);// 0 订阅 1 取消订阅

        void onDeleteChannel(int pos);
    }

    //region ViewHolder Header and Footer
    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.channel_home_item_picture_fl)
        FrameLayout item_fl;
        @Bind(R.id.channel_list_itme_image)
        ImageView itme_image;
        @Bind(R.id.channel_home_item_video_play)
        ImageView play_icon;
        @Bind(R.id.my_channel_delete_area_second)
        LinearLayout delete_area;
        @Bind(R.id.channel_home_item_duration)
        TextView item_duration;
        @Bind(R.id.channel_list_item_content)
        TextView item_content;
        @Bind(R.id.channel_list_item_name)
        TextView item_name;
        @Bind(R.id.channel_list_item_date)
        TextView item_date;
        @Bind(R.id.channel_list_item_count)
        TextView item_count;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ChannelExpResult data) {

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) item_fl.getLayoutParams();

            Resources resources = mContext.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            int width = dm.widthPixels;
            params.height = width * 10 / 16;
            item_fl.setLayoutParams(params);

            Glide.with(mContext)
                    .load(data.thumbnail)
                    .placeholder(R.drawable.ic_empty_small)
                    .error(R.drawable.ic_empty_small)
                    .bitmapTransform(new CropSquareTransformation(mContext))
                    .centerCrop()
                    .into(itme_image);
            item_content.setText(data.subject);
            item_date.setText(DateUtils.getTimeState(String.valueOf(data.creation_time), ""));
            item_count.setText("浏览数 " + data.view_count);
            if (data.content.type.equals("VIDEO")) {
                play_icon.setVisibility(View.VISIBLE);
                item_duration.setVisibility(View.VISIBLE);
                try {
                    if (data.content.video != null)
                        item_duration.setText(DateUtils.getDurtationTime(((long) (data.content.video.duration * 1000 + 500))));
                    else {
                        item_duration.setText("0:00");
                    }
                } catch (Exception e) {
                    NLog.e("CHP", e.toString());
                }

            } else {
                play_icon.setVisibility(View.GONE);
                item_duration.setVisibility(View.GONE);
            }
            delete_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDeleteChannel(getLayoutPosition() - 1);
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.channel_user_avatar)
        ImageView channel_user_avatar;
        @Bind(R.id.channel_home_header_name)
        TextView channel_header_name;
        @Bind(R.id.channel_header_owner_name)
        TextView channel_header_owner_name;
        @Bind(R.id.channel_header_create_time)
        TextView channel_header_create_time;

        @Bind(R.id.channel_header_experiences_summary)
        TextView header_experiences_summary;
        @Bind(R.id.channel_header_pulldown)
        LinearLayout channel_header_pulldown;
        @Bind(R.id.channel_header_subscribe_text)
        TextView subscribe_text;
        @Bind(R.id.channel_header_subscribe_no)
        TextView subscribe_no;
        @Bind(R.id.channel_header_subscribe_ll)
        LinearLayout subscribe_ll;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ChannelInfoBean bean) {
            Glide.with(mContext)
                    .load(bean.thumbnail)
                    .placeholder(R.drawable.channel_default_diagram)
                    .error(R.drawable.channel_default_diagram)
                    .bitmapTransform(new CropSquareTransformation(mContext))
                    .centerCrop()
                    .into(channel_user_avatar);
            channel_header_name.setText(bean.name);
            if (!TextUtils.isEmpty(bean.owner.name))
                channel_header_owner_name.setText(bean.owner.name);
            channel_header_create_time.setText("创建于 " + DateUtils.longToString(bean.creation_time));
            if (bean.subscribed) {
                subscribe_text.setText("取消订阅");
                subscribe_text.setSelected(true);
            } else {
                subscribe_text.setText("订阅频道");
                subscribe_text.setSelected(false);
            }
            if (bean.mine) {
                subscribe_ll.setClickable(false);
            }
            header_experiences_summary.setText(bean.description);
            subscribe_no.setText(String.valueOf(bean.subscriber_count));
        }

        @OnClick({R.id.channel_header_experiences_summary, R.id.channel_header_pulldown, R.id.channel_header_subscribe_ll})
        @Override
        public void onClick(View v) {
            int v_id = v.getId();
            switch (v_id) {
                case R.id.channel_header_experiences_summary:
                    header_experiences_summary.setVisibility(View.GONE);
                    channel_header_pulldown.setVisibility(View.VISIBLE);
                    break;
                case R.id.channel_header_pulldown:
                    header_experiences_summary.setVisibility(View.VISIBLE);
                    channel_header_pulldown.setVisibility(View.GONE);
                    break;
                case R.id.channel_header_subscribe_ll:
                    boolean flg = subscribe_text.isSelected();
                    subscribe_text.setSelected(flg ? false : true);
                    mListener.onSubscribeViewClick(flg ? 1 : 0);
                    break;
            }
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
