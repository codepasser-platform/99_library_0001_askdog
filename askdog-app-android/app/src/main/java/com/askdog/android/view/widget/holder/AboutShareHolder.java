package com.askdog.android.view.widget.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.askdog.android.R;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Administrator on 2016/8/6.
 */
public class AboutShareHolder extends TreeNode.BaseNodeViewHolder<AboutShareHolder.DetailAboutShareItem>{
    private ImageView expandIcon;

    public AboutShareHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, AboutShareHolder.DetailAboutShareItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_node_share, null, false);
        expandIcon = (ImageView) view.findViewById(R.id.detail_share_expand);
        expandIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tView.toggleNode(node);
            }
        });
        return view;
    }

    @Override
    public void toggle(boolean active) {
        if(active){
            expandIcon.setImageResource(R.drawable.icon_pull_down);
        }else{
            expandIcon.setImageResource(R.drawable.icon_pull_up);
        }
    }



    public static class DetailAboutShareItem {
        public String sharename;

        public DetailAboutShareItem() {

        }
    }
}
