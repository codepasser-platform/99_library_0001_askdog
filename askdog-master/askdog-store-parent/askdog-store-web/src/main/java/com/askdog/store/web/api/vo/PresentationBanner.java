package com.askdog.store.web.api.vo;

import com.askdog.common.Out;
import com.askdog.store.service.bo.BannerDetail;

public class PresentationBanner implements Out<PresentationBanner, BannerDetail> {

    private String screenPicture;

    private String goodsPicture;

    private String goodsId;

    private String title;

    private String summary;

    private long pointPrice;


    public String getScreenPicture() {
        return screenPicture;
    }

    public void setScreenPicture(String screenPicture) {
        this.screenPicture = screenPicture;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public long getPointPrice() {
        return pointPrice;
    }

    public void setPointPrice(long pointPrice) {
        this.pointPrice = pointPrice;
    }

    @Override
    public PresentationBanner from(BannerDetail detail) {
        this.goodsId = detail.getGoodsId();
        this.title = detail.getTitle();
        this.summary = detail.getSummary();
        this.pointPrice = detail.getPointPrice();
        this.goodsPicture = detail.getGoodsPicture();
        this.screenPicture = detail.getScreenPicture();
        return this;
    }
}
