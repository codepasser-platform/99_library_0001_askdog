package com.askdog.android.view.widget.holder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.interf.ICallback;
import com.askdog.android.utils.transformations.glide.CropCircleTransformation;
import com.bumptech.glide.Glide;
import com.unnamed.b.atv.model.TreeNode;


/**
 * Created by Administrator on 2016/8/6.
 */
public class DetailRecommendHolder extends TreeNode.BaseNodeViewHolder<DetailRecommendHolder.DetailRecommendItem> {
    ICallback callback;
    TextView release;
    EditText content;
    private LinearLayout inputArea;
    private TextView cancle;
    private ImageView expandIcon;
    private ImageView avatar;
    private TextView times;

    public DetailRecommendHolder(Context context, ICallback callback) {
        super(context);
        this.callback = callback;
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count > 0)
                release.setEnabled(true);
            else
                release.setEnabled(false);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public View createNodeView(final TreeNode node, final DetailRecommendItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_node_recommend, null, false);
        avatar = (ImageView) view.findViewById(R.id.detail_recommend_avatar);
        cancle = (TextView) view.findViewById(R.id.detail_recommend_cancle);
        release = (TextView) view.findViewById(R.id.detail_recommend_release);
        content = (EditText) view.findViewById(R.id.detail_recommend_text);
        inputArea = (LinearLayout) view.findViewById(R.id.detail_recommend_input);
        expandIcon = (ImageView) view.findViewById(R.id.detail_recommend_expand);
        times = (TextView) view.findViewById(R.id.detail_recommend_times);
        inputArea.setVisibility(View.GONE);

        expandIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tView.toggleNode(node);
            }
        });
        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.OnRealeaseClick(node, content.getText().toString().trim(), value);
                content.setText("");
                InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(content.getWindowToken(), 0);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.setText("");
            }
        });
        bindViewData(value);
        return view;
    }

    private void bindViewData(final DetailRecommendItem value) {
        Glide.with(context)
                .load(value.avatarUrl)
                .error(R.drawable.avatar_default)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(avatar);
        String pinglun = "评论 (" + value.count + ")";

        times.setText(pinglun);
    }

    public void updateCount(int no){
        String pinglun = "评论 (" + no + ")";

        times.setText(pinglun);
    }
    @Override
    public void toggle(boolean active) {
        if (active) {
            expandIcon.setImageResource(R.drawable.icon_pull_down);
            inputArea.setVisibility(View.VISIBLE);
        } else {
            expandIcon.setImageResource(R.drawable.icon_pull_up);
            inputArea.setVisibility(View.GONE);
        }
    }

    public static class DetailRecommendItem {
        public boolean vip;
        public String avatarUrl;
        public int count;

        public DetailRecommendItem(boolean vip, String avatarUrl, int count) {
            this.vip = vip;
            this.avatarUrl = avatarUrl;
            this.count = count;
        }
    }
}
