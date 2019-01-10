package com.askdog.store.service.bo;

import com.askdog.common.Out;

public class BannerDetail implements Out<BannerDetail, GoodsDetail> {

    private String screenPicture;

    private String goodsPicture;

    private String goodsId;

    private String title;

    private String summary;

    private long pointPrice;

    public String getScreenPicture() {
        return screenPicture;
    }

    public BannerDetail setScreenPicture(String screenPicture) {
        this.screenPicture = screenPicture;
        return this;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public BannerDetail setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
        return this;
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
    public BannerDetail from(GoodsDetail goodsDetail) {
        this.goodsId = goodsDetail.getId();
        this.title = goodsDetail.getName();
        this.summary = goodsDetail.getSummary();
        this.pointPrice = goodsDetail.getPointsPrice();
        return this;
    }

}
