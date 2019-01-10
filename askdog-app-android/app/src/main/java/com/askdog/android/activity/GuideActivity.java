package com.askdog.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.askdog.android.R;
import com.askdog.android.adapter.ViewPagerAdapter;
import com.askdog.android.utils.ConstantUtils;
import com.askdog.android.utils.PreferencesUtils;

import java.util.ArrayList;

public class GuideActivity extends Activity implements OnPageChangeListener {

    private ViewPager guidePages;
    private ArrayList<View> pageViews;
    private ImageView imageView;
    private ImageView[] imageViews;
    private ViewGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<>();
        pageViews.add(inflater.inflate(R.layout.activity_guide_item1, null));
        pageViews.add(inflater.inflate(R.layout.activity_guide_item2, null));
        pageViews.add(inflater.inflate(R.layout.activity_guide_item3, null));
        pageViews.add(inflater.inflate(R.layout.activity_guide_item4, null));

        imageViews = new ImageView[pageViews.size()];
        guidePages = (ViewPager) findViewById(R.id.guidePages);

        guidePages.setAdapter(new ViewPagerAdapter(pageViews));
        guidePages.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        //点击最后一页，跳转到主页
        if (position == 3) {
            View itemView = pageViews.get(position);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    PreferencesUtils.putBoolean(GuideActivity.this, ConstantUtils.IS_FIRST_RUN, false);
                    Intent intent = new Intent(GuideActivity.this, AskDogActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

}
