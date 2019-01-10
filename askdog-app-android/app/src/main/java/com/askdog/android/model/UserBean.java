package com.askdog.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wyong on 2016/8/11.
 */
public class UserBean implements Parcelable {
    public String id;
    public String name;
    public String avatar;
    public String gender;
    public String signature;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.gender);
        dest.writeString(this.signature);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.gender = in.readString();
        this.signature = in.readString();
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
