package com.askdog.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.activity.ChannelHomeActivity;
import com.askdog.android.activity.PersonalChannelActivity;
import com.askdog.android.model.CashWithdrawBean;
import com.askdog.android.model.NotificationBean;
import com.askdog.android.model.UserBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/3.
 */
public class NotificationRvAdapter extends RecyclerView.Adapter<NotificationRvAdapter.ViewHolder> {
    private ArrayList<NotificationBean.NofifyResult.GroupData> mItemsData;
    private Context mContext;

    public NotificationRvAdapter(Context context, ArrayList<NotificationBean.NofifyResult.GroupData> datas) {
        this.mContext = context;
        this.mItemsData = datas;
    }

    @Override
    public NotificationRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.holder_notification_sub_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationRvAdapter.ViewHolder holder, int position) {
        NotificationBean.NofifyResult.GroupData bean = mItemsData.get(position);
        holder.bindData(bean);
    }

    @Override
    public int getItemCount() {
        return mItemsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.holder_notification_item_from)
        TextView from;
        @Bind(R.id.holder_notification_item_type)
        TextView type;
        @Bind(R.id.holder_notification_item_to)
        TextView to;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(final NotificationBean.NofifyResult.GroupData bean) {
            from.setText(bean.content.user.name);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    UserBean user = new UserBean();
                    user.id = bean.content.user.id;
//                    user.avatar = bean.content.user.;
                    user.name = bean.content.user.name;
//                    user.signature = mExperiencesBean.author.signature;

                    Intent mIntent = new Intent();
                    mIntent.setClass(mContext, PersonalChannelActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", user);

                    mIntent.putExtras(bundle);
                    mContext.startActivity(mIntent);
                }
            });
            String typeValue = "未知";
            if(bean.content.type.equals("CREATE_EXPERIENCE_COMMENT")){
                typeValue = "评论";
            }else if(bean.content.type.equals("CREATE_EXPERIENCE_COMMENT_COMMENT")){
                typeValue = "回复";
            }else if(bean.content.type.equals("ACCEPT_GOLD_BUCKET")){
                typeValue = "一桶金审核通过";
            }else if(bean.content.type.equals("REJECT_GOLD_BUCKET")){
                typeValue = "一桶金审核拒绝";
            }
            type.setText(typeValue);
            to.setText(bean.content.target.description);
        }
    }

}
