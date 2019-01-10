package com.askdog.android.view.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.model.HomeBean;
import com.askdog.android.utils.DateUtils;
import com.askdog.android.utils.transformations.glide.CropSquareTransformation;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyong
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private OnRecyclerViewItemClickListener callback;
    private Context mContext;
    private List<HomeBean.Result> dataList;


    public RecyclerViewAdapter(Context context, List<HomeBean.Result> dataList, OnRecyclerViewItemClickListener callback) {
        this.dataList = dataList;
        this.callback = callback;
        mContext = context;
    }

    public List<HomeBean.Result> getDataList() {
        return dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);

        return new ViewHolder(v, callback);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        holder.title.setText(dataList.get(position).subject);
        if (position > getItemCount()) return;
        HomeBean.Result result = dataList.get(position);
        holder.bindData(result);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(String userId, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnRecyclerViewItemClickListener listener;
        @Bind(R.id.item_picture_fl)
        FrameLayout item_fl;
        @Bind(R.id.itme_image)
        ImageView itme_image;
        @Bind((R.id.item_duration))
        TextView duration;
        @Bind(R.id.item_video_play)
        ImageView video_play;
        @Bind(R.id.item_content)
        TextView item_content;

        @Bind(R.id.item_channel)
        TextView item_channel;

        @Bind(R.id.item_date)
        TextView item_date;
        @Bind(R.id.item_count)
        TextView item_count;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        @OnClick({R.id.card_view})
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.card_view:
                    int pos = getLayoutPosition();
                    listener.onItemClick(dataList.get(pos).id, getLayoutPosition());
                    break;
            }
        }

        public void bindData(HomeBean.Result result) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) item_fl.getLayoutParams();

            Resources resources = mContext.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            int width = dm.widthPixels;
            int height = width * 10 / 16;
            params.height = height;
            item_fl.setLayoutParams(params);

//            String url = result.content_pic_url + "@" + height + "h_" + width + "w_1e_1c";
            Glide.with(mContext)
                    .load(result.content_pic_url)
                    .placeholder(R.drawable.ic_empty_small)
                    .error(R.drawable.ic_empty_small)
                    .bitmapTransform(new CropSquareTransformation(mContext))
                    .centerCrop()
                    .into(itme_image);

            item_content.setText(result.subject);
            item_channel.setText(result.channel.channel_name);
            item_date.setText((DateUtils.getTimeState(String.valueOf(result.creation_date), "")));
            item_count.setText("浏览数 " + result.view_count);
            if (result.content_type.equals("VIDEO")) {
                duration.setVisibility(View.VISIBLE);
                video_play.setVisibility(View.VISIBLE);
                duration.setText(DateUtils.getDurtationTime(((long) (result.video_duration * 1000 + 500))));
            } else {
                duration.setVisibility(View.GONE);
                video_play.setVisibility(View.GONE);
            }
        }
    }

}