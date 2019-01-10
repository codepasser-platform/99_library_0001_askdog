package com.askdog.android.view.widget.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.interf.ICallback;
import com.askdog.android.utils.DateUtils;
import com.askdog.android.utils.transformations.glide.CropCircleTransformation;
import com.bumptech.glide.Glide;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Administrator on 2016/8/6.
 */
public class DetailRecommendItemHolder extends TreeNode.BaseNodeViewHolder<DetailRecommendItemHolder.DetailRecommendSubItem> {
    ICallback callback;
    private ImageView avatar;
    private TextView name;
    private TextView content;
    private TextView time;
    private LinearLayout expand_ll;
    private TextView item_expand;
    private TextView count;
    private TextView answer;

    public DetailRecommendItemHolder(Context context, ICallback callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    public View createNodeView(final TreeNode node, final DetailRecommendSubItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_detail_recommend_item, null, false);
        avatar = (ImageView) view.findViewById(R.id.detail_recommend_item_avatar);
        name = (TextView) view.findViewById(R.id.datail_recommend_item_name);
        content = (TextView) view.findViewById(R.id.datail_recommend_item_content);
        time = (TextView) view.findViewById(R.id.datail_recommend_item_date);
        answer = (TextView) view.findViewById(R.id.datail_recommend_item_answer);
        expand_ll = (LinearLayout) view.findViewById(R.id.detail_recommend_item_expand_ll);
        item_expand = (TextView) view.findViewById(R.id.detail_recommend_item_expand);
        count = (TextView) view.findViewById(R.id.detail_recommend_item_count);
        item_expand.setSelected(true);
        view.findViewById(R.id.detail_recommend_item_expand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item_expand.isSelected()) {
                    callback.expandSecondList(node, true, value.id);
                    item_expand.setSelected(false);
                    item_expand.setText("收起");
                } else {
                    callback.expandSecondList(node, false, null);
                    item_expand.setSelected(true);
                    item_expand.setText("查看更多");
                }
            }
        });
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value.level == 1)
                    callback.onAnswerCommend(node, value.owner_id, value.id);
                if (value.level == 2)
                    callback.onAnswerCommend(node.getParent(), value.owner_id, value.reply_comment_id);
            }
        });
        initView(value);
        return view;
    }

    private void initView(DetailRecommendSubItem value) {
        Glide.with(context)
                .load(value.avatarUrl)
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_default)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(avatar);
        name.setText(value.uName);
        content.setText(value.mContent);
        time.setText(DateUtils.getTimeState(value.mTime, ""));
        if (value.total > 2 && value.level == 1) {
            expand_ll.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(value.total) + "条回复");
        } else {
            expand_ll.setVisibility(View.GONE);
        }
    }

    @Override
    public int getContainerStyle() {
        return R.style.TreeNodeStyleCustom;
    }

    public static class DetailRecommendSubItem {
        public String avatarUrl;
        public String uName;
        public String mContent;
        public String mTime;
        public int total;
        public int level;
        public String reply_comment_id;              // 回复的评论的ID,只有二级评论存在该字段
        public String owner_id;
        public String id;

        public DetailRecommendSubItem() {

        }
    }
}
