package com.askdog.android.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.interf.IOnClickListener;
import com.askdog.android.model.HistoryBean;
import com.askdog.android.utils.DateUtils;
import com.askdog.android.utils.transformations.glide.CropSquareTransformation;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/14.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


    private List<HistoryBean.HistoryResult> dataList;
    private Context mContext;
    private IOnClickListener mListener;
    private boolean isDeleteEnable = false;

    public HistoryAdapter(Context context, List<HistoryBean.HistoryResult> list, IOnClickListener listener) {
        mContext = context;
        dataList = list;
        mListener = listener;
    }

    public void setDeleteEnable(boolean enable) {
        isDeleteEnable = enable;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_all_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HistoryBean.HistoryResult bean = dataList.get(position);
        holder.bindData(bean);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<HistoryBean.HistoryResult> getDataList() {
        return dataList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.result_thumbnail)
        ImageView result_thumbnail;
        @Bind(R.id.result_content_content)
        TextView result_content_content;
        @Bind(R.id.result_view_count)
        TextView result_view_count;

        @Bind(R.id.result_creation_date)
        TextView result_creation_date;
        @Bind(R.id.result_author_name)
        TextView result_author_name;
        @Bind(R.id.result_channel_name)
        TextView result_channel_name;

        @Bind(R.id.history_delete_menu)
        ImageButton delete_menu;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final HistoryBean.HistoryResult result) {
            Glide.with(mContext)
                    .load(result.thumbnail)
                    .placeholder(R.drawable.ic_empty_small)
                    .error(R.drawable.ic_empty_small)
                    .bitmapTransform(new CropSquareTransformation(mContext))
                    .centerCrop()
                    .into(result_thumbnail);

            result_content_content.setText(Html.fromHtml(result.subject.replace("<mark>", "<font color=\"#3688e5\">").replace("</mark>", "</font>")));
            result_view_count.setText("浏览数 " + result.view_count);
            result_creation_date.setText(DateUtils.getTimeState(String.valueOf(result.creation_time), ""));
            result_author_name.setText(result.author.name);
            result_channel_name.setText(result.channel.name);
            if(isDeleteEnable){
                delete_menu.setVisibility(View.VISIBLE);
                delete_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getLayoutPosition();
                        mListener.onItemDeleteClickListener(pos);

                    }
                });
            }

            result_thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getLayoutPosition();
                    mListener.onItemViewClickListener(pos);
                }
            });
            result_content_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getLayoutPosition();
                    mListener.onItemViewClickListener(pos);
                }
            });
            result_author_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getLayoutPosition();
                    mListener.onItemViewClickListener(pos);
                }
            });
        }
    }
}