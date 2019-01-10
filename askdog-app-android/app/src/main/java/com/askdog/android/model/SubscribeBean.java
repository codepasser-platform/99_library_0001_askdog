package com.askdog.android.model;

/**
 * Created by lisong on 2016/8/4.
 */
public class SubscribeBean {

    private String imagePath;
    private String title;
    private boolean isSelect;
    private int imageId;

    public String getImagePath(){
        return this.imagePath;
    }
    public  String getTitle(){
        return this.title;
    }
    public  boolean isSelect(){
        return this.isSelect;
    }
    public int getImageId(){
        return this.imageId;
    }
    public void setImagePath(String imagePath){
        this.imagePath=imagePath;
    }

    public void setTitle(String title){
        this.title=title;
    }
    public void setImageId(int imageId){
        this.imageId=imageId;
    }
    public void setSelect(boolean isSelect){
        this.isSelect=isSelect;
    }
}
