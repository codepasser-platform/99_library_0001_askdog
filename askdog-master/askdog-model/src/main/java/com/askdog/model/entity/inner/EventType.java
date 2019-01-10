package com.askdog.model.entity.inner;

public enum EventType {

    FEEDBACK_SEND("反馈意见"),
    FEEDBACK_ACCEPT("反馈意见被采纳"),

    REGISTER_SEND("用户注册申请"),
    REGISTER_CONFIRM("用户注册确认"),
    LOGIN("用户登录"),
    PERFECT_PERSONAL_INFORMATION("完善个人信息"),

    CREATE_QUESTION("创建问题"),
    UPDATE_QUESTION("更新问题"),
    UP_VOTE_QUESTION("赞问题"),
    DOWN_VOTE_QUESTION("踩问题"),
    SHARE_QUESTION("分享问题"),
    SHARE_QUESTION_NEARBY("分享问题给附近人"),
    FOLLOW_QUESTION("关注问题"),
    UN_FOLLOW_QUESTION("取消关注问题"),
    VIEW_QUESTION("浏览问题"),
    SHARE_EXPERIENCE("分享经验"),
    REPORT_QUESTION("举报问题"),
    DELETE_QUESTION("删除问题"),

    CREATE_QUESTION_COMMENT("评论问题"),
    REPORT_QUESTION_COMMENT("举报问题评论"),

    CREATE_ANSWER("创建回答"),
    UPDATE_ANSWER("更新问题"),
    UP_VOTE_ANSWER("赞回答"),
    DOWN_VOTE_ANSWER("踩回答"),
    FAV_ANSWER("收藏回答"),
    REPORT_ANSWER("举报回答"),
    DELETE_ANSWER("删除回答"),

    CREATE_ANSWER_COMMENT("评论回答"),
    REPORT_ANSWER_COMMENT("举报回答评论");

    private String description;

    EventType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}