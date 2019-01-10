package com.askdog.android.view.widget.holder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.adapter.NotificationRvAdapter;
import com.askdog.android.model.NotificationBean;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Administrator on 2016/9/3.
 */
public class NotificationItemHolder extends TreeNode.BaseNodeViewHolder<NotificationBean.NofifyResult> {
    private TextView notificationDate;
    private RecyclerView recyclerView;
    private NotificationRvAdapter mNotificationRvAdapter;
    public NotificationItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node,NotificationBean.NofifyResult value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.holder_notification_item, null, false);
        notificationDate = (TextView) view.findViewById(R.id.holder_notification_date);
        recyclerView = (RecyclerView) view.findViewById(R.id.holder_notification_rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        notificationDate.setText(value.group_date.y + "." + value.group_date.m + "." + value.group_date.d);
        mNotificationRvAdapter = new NotificationRvAdapter(context,value.group_data);
        recyclerView.setAdapter(mNotificationRvAdapter);
        return view;
    }

}
