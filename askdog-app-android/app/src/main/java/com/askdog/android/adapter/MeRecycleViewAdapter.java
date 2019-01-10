package com.askdog.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.model.MeBean;
import com.askdog.android.view.widget.RecyclerItemClickListener;
import com.askdog.android.view.widget.RecyclerViewAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/4.
 */
public class MeRecycleViewAdapter  extends RecyclerView.Adapter<MeRecycleViewAdapter.ViewHolder>  implements View.OnClickListener{

    private final List<MeBean> dataList;
    private final Context mContext;
    private OnMyRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnMyRecyclerViewItemClickListener {
        void onItemClick(View view , int data);
    }
    public MeRecycleViewAdapter(Context context, List<MeBean> dataList,OnMyRecyclerViewItemClickListener listener) {
        this.dataList = dataList;
        mOnItemClickListener = listener;
        mContext = context;
    }

    @Override
    public MeRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_me_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MeBean result = dataList.get(position);
        holder.itemView.setTag(position);
        holder.bindData(result);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(Integer)v.getTag());
        }
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.me_item_icon)
        ImageView me_item_icon;
        @Bind(R.id.me_item_title)
        TextView me_item_title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(MeBean result) {
            me_item_icon.setImageResource(result.getMe_item_icon());
            me_item_title.setText(result.getMe_item_name());
        }
    }
}
