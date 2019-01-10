package com.askdog.android.activity.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.askdog.android.BaseActivity;
import com.askdog.android.R;
import com.askdog.android.activity.ExperiencesDetailActivity;
import com.askdog.android.model.CategoriesInfoBean;
import com.askdog.android.model.ChannelBean;
import com.askdog.android.model.ExperiencesBean;
import com.askdog.android.model.WordPostBean;
import com.askdog.android.network.service.OkRequestAction;
import com.askdog.android.network.service.OkRequestConstants;
import com.askdog.android.utils.JsonMananger;
import com.askdog.android.utils.NLog;
import com.askdog.android.view.pickerview.OptionsPopupWindow;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareWordTwoActivity extends BaseActivity {
    private final String TAG = ShareWordTwoActivity.class.getSimpleName();
    @Bind(R.id.navi_title)
    TextView title;
    @Bind(R.id.navi_right)
    TextView releaseBtn;

    @Bind(R.id.share2_channel_rl)
    RelativeLayout share2_channel_rl;

    @Bind(R.id.share2_channel_name)
    TextView channel_name;
    @Bind(R.id.share2_category_name)
    TextView category_name;
    private String mdContent;
    private OptionsPopupWindow categoryOptions;
    private ArrayList<String> categoryOptionsItems = new ArrayList<>();
    private OptionsPopupWindow channelOptions;
    private ArrayList<String> channelOptionsItems = new ArrayList<>();
    private ChannelBean mChannelBean;
    private List<CategoriesInfoBean> categoies;


    private String channelId;
    private String categoryId;
    private String subject;
    private ArrayList<String> linkIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_word_two);
        ButterKnife.bind(this);
        initView();
        intiData();
        request(OkRequestConstants.CODE_USER_CHANNELS_OWNED);
        request(OkRequestConstants.CODE_CATEGORIES_INFO);
    }

    private void intiData() {
        mdContent = getIntent().getStringExtra("content");
        subject = getIntent().getStringExtra("subject");
        linkIds = getIntent().getStringArrayListExtra("picture_link_ids");

    }


    private void initView() {
        title.setText("经验分享");
        releaseBtn.setText("发布");
        releaseBtn.setTextColor(ContextCompat.getColor(this, R.color.blue));
        releaseBtn.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.share2_category_rl, R.id.share2_channel_rl, R.id.navi_right, R.id.navi_back})
    @Override
    public void onClick(View v) {
        int vid = v.getId();
        switch (vid) {
            case R.id.share2_category_rl:
                showCategoryDialog();
                break;
            case R.id.share2_channel_rl:
                showChannelDialog();
                break;
            case R.id.navi_right:
                boolean flg = checkChannelAndCategory();
                if (flg) {
                    request(OkRequestConstants.CODE_CREATE_WORD);
                }
                break;
            case R.id.navi_back:
                finish();
                break;
        }
    }

    private String buildAndSendRelease() {
        WordPostBean mWordPostBean = new WordPostBean();
        WordPostBean.WContent content = new WordPostBean.WContent();
        content.type = "TEXT";
        content.content = mdContent;
        content.picture_link_ids = linkIds;
        mWordPostBean.category_id = categoryId;
        mWordPostBean.channel_id = channelId;
        mWordPostBean.price = 0;
        mWordPostBean.subject = subject;
        mWordPostBean.content = content;
        try {
            return JsonMananger.beanToJson(mWordPostBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkChannelAndCategory() {
        //TODO
        // category_name.getText().toString().equalsIgnoreCase("未选择")
        return channel_name.isSelected() && category_name.isSelected();
    }

    private void showCategoryDialog() {
        //选项选择器
        categoryOptions.showAtLocation(share2_channel_rl, Gravity.BOTTOM, 0, 0);
    }

    private void buildCategoryDialog(String result) {
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<CategoriesInfoBean>>() {
        }.getType();
        categoies = gson.fromJson(result, type);

        //选项1
        if (categoies != null) {
            categoryOptions = new OptionsPopupWindow(this);
            int size = categoies.size();
            for (int i = 0; i < size; i++) {
                CategoriesInfoBean value = categoies.get(i);
                categoryOptionsItems.add(value.name);
            }
            categoryOptions.setPicker(categoryOptionsItems);
            categoryOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    String tx = categoryOptionsItems.get(options1);
                    categoryId = categoies.get(options1).id;
                    category_name.setText(tx);
                    category_name.setSelected(true);
                }
            });
        }
    }

    private void showChannelDialog() {
        channelOptions.showAtLocation(share2_channel_rl, Gravity.BOTTOM, 0, 0);
    }


    private void buildChannelDialog(final ChannelBean mChannel) {
        //选项选择器
        channelOptions = new OptionsPopupWindow(this);

        //选项1
        ArrayList<ChannelBean.ChannelResult> results = mChannel.result;
        for (int i = 0; i < results.size(); i++) {
            ChannelBean.ChannelResult result = results.get(i);
            channelOptionsItems.add(result.name);
        }

        channelOptions.setPicker(channelOptionsItems);
        channelOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = channelOptionsItems.get(options1);
                channelId = mChannelBean.result.get(options1).id;
                channel_name.setText(tx);
                channel_name.setSelected(true);
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Object doInBackground(int requestCode) throws Exception {
//        OkRequestAction action = new OkRequestAction(this);
        if (OkRequestConstants.CODE_USER_CHANNELS_OWNED == requestCode) {
            return action.getMyOwnedChannel(0, 100);
        } else if (OkRequestConstants.CODE_CATEGORIES_INFO == requestCode) {
            return action.getCategoriesInfo();
        } else if (OkRequestConstants.CODE_CREATE_WORD == requestCode) {
            String json = buildAndSendRelease();
            return action.postCreateWordExp(json);
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, String result) {
        super.onSuccess(requestCode, result);
        try {
            if (OkRequestConstants.CODE_USER_CHANNELS_OWNED == requestCode) {
                mChannelBean = JsonMananger.jsonToBean(result, ChannelBean.class);
                buildChannelDialog(mChannelBean);
            } else if (OkRequestConstants.CODE_CATEGORIES_INFO == requestCode) {
                buildCategoryDialog(result);
            } else if (requestCode == OkRequestConstants.CODE_CREATE_WORD) {
                JsonObject obj = new JsonObject();
                ExperiencesBean bean = JsonMananger.jsonToBean(result, ExperiencesBean.class);
                if (bean != null) {
                    Intent intent = new Intent();
                    intent.setClass(ShareWordTwoActivity.this,ExperiencesDetailActivity.class);
                    intent.putExtra("authorId",bean.id);
                    intent.putExtra("from","1");
                    startActivity(intent);
                }
                NLog.d(TAG, result);
            }
        } catch (Exception e) {
            NLog.d("Share", e.toString());
        }

    }
}
