package com.askdog.android.view.widget.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.R;
import com.askdog.android.interf.ICallback;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Administrator on 2016/8/25.
 */
public class LoadMoreHolder extends TreeNode.BaseNodeViewHolder<LoadMoreHolder.LoadMoreItem> {
    private final ICallback callback;

    public LoadMoreHolder(Context context, ICallback callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    public View createNodeView(final TreeNode node, final LoadMoreHolder.LoadMoreItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_node_loadmore, null);
        TextView textView = (TextView) view.findViewById(R.id.holder_load_more_notice);
        if (value.isBottom) {
            textView.setText("到底了");
        }else {
            textView.setText("点击加载更多");
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!value.isBottom){
                    callback.onLoadMoreClick();
                }
            }
        });
        return view;
    }

    @Override
    public void toggle(boolean active) {

    }

    public static class LoadMoreItem {
        public boolean isBottom;

        public LoadMoreItem(boolean isBottom) {
            this.isBottom = isBottom;
        }
    }
}
