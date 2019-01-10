package com.askdog.android.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.model.SubscribeBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SubscribeActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = "SubscribeActivity";

    @Bind(R.id.tab_login_title)
    TextView titleTextView;
    @Bind(R.id.subscribe_gridview)
    GridView mGridView;
    private ImageAdapter mImageAdapter;
    @Bind(R.id.subscribe_goto_layout_textview)
    TextView gotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        ButterKnife.bind(this);
        initView();
        // 中间按键图片触发
    }

    ArrayList<SubscribeBean> list;

    private void initView() {
        titleTextView.setText("订阅频道");
        gotoView.setOnClickListener(this);
        list = new ArrayList<SubscribeBean>();
        for (int index = 0; index < 10; index++) {
            SubscribeBean item = new SubscribeBean();
            item.setTitle("title  " + index);
            item.setImageId(R.drawable.test1);
            list.add(item);
        }
        mImageAdapter = new ImageAdapter(this);

        mGridView.setAdapter(mImageAdapter);
        // 九宫格 点击事件
        mGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index,
                                    long arg3) {
                SubscribeBean item = list.get(index);
                ImageView selectView = (ImageView) arg1
                        .findViewById(R.id.subscribe_gridview_select_image);
                if (item.isSelect()) {
                    item.setSelect(false);
                    selectView.setVisibility(View.INVISIBLE);
                } else {
                    item.setSelect(true);
                    selectView.setVisibility(View.VISIBLE);
                }


            }
        });
        mImageAdapter.setData(list);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent mIntent;
        switch (id) {
            case R.id.subscribe_goto_layout_textview:
                break;
            default:
                break;
        }
    }

    // 九宫格显示逻辑
    private class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private ArrayList<SubscribeBean> mList = new ArrayList<SubscribeBean>();

        public ImageAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        private void setData(ArrayList<SubscribeBean> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(
                        R.layout.subscribe_gridview_item, null);
            }
            ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.subscribe_gridview_item_image);
            ImageView selectView = (ImageView) convertView
                    .findViewById(R.id.subscribe_gridview_select_image);
            TextView titleView = (TextView) convertView
                    .findViewById(R.id.subscribe_gridview_item_title);

            SubscribeBean bean = mList.get(position);
            imageView.setImageResource(bean.getImageId());
            titleView.setText(bean.getTitle());
            if (bean.isSelect()) {
                selectView.setVisibility(View.VISIBLE);
            } else {
                selectView.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }

}
