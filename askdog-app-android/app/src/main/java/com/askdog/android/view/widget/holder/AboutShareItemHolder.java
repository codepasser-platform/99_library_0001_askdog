package com.askdog.android.view.widget.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.askdog.android.R;
import com.askdog.android.utils.DateUtils;
import com.askdog.android.utils.NToast;
import com.askdog.android.utils.transformations.glide.CropSquareTransformation;
import com.bumptech.glide.Glide;
import com.unnamed.b.atv.model.TreeNode;


public class AboutShareItemHolder extends TreeNode.BaseNodeViewHolder<AboutShareItemHolder.ShareItem> {


    private ImageView share_content_pic;
    private TextView share_subject;
    private TextView share_creation_date;
    private TextView share_view_count;
    private TextView share_author_name;
    private TextView share_channel_name;

    public AboutShareItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, ShareItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_node_share_item, null, false);
        share_content_pic = (ImageView) view.findViewById(R.id.share_content_pic);
        share_subject = (TextView) view.findViewById(R.id.share_subject);
        share_creation_date = (TextView) view.findViewById(R.id.share_creation_date);
        share_view_count = (TextView) view.findViewById(R.id.share_view_count);
        share_author_name = (TextView) view.findViewById(R.id.share_author_name);
        share_channel_name = (TextView) view.findViewById(R.id.share_channel_name);
        initView(value);
        return view;
    }

    private void initView(ShareItem value) {
        Glide.with(context)
                .load(value.share_content_pic)
                .placeholder(R.drawable.ic_empty_small)
                .error(R.drawable.ic_empty_small)
                .bitmapTransform(new CropSquareTransformation(context))
                .centerCrop()
                .into(share_content_pic);
        share_subject.setText(value.share_subject);
        share_creation_date.setText(DateUtils.getTimeState(String.valueOf(value.share_creation_date),""));
        share_view_count.setText("浏览数"+String.valueOf(value.share_view_count));
        share_author_name.setText(value.share_author_name);
        share_channel_name.setText(value.share_channel_name);

    }

    @Override
    public void toggle(boolean active) {
        NToast.shortToast(context,"当前状态 = " + active);
    }


    public static class ShareItem {
        public String share_content_pic;
        public String share_subject;
        public long share_creation_date;
        public int share_view_count;
        public String share_author_name;
        public String share_channel_name;
        public String id;
        public ShareItem() {
        }
        // rest will be hardcoded
    }

}
