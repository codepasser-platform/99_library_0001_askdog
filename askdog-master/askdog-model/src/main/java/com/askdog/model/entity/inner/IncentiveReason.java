package com.askdog.model.entity.inner;

public enum IncentiveReason {

    FEEDBACK_SEND("反馈意见"),
    FEEDBACK_ACCEPT("反馈意见被采纳"),

    REGISTER_CONFIRM("用户注册确认"),
    LOGIN("用户登录"),
    PERFECT_PERSONAL_INFORMATION("完善个人信息"),

    CREATE_QUESTION("创建问题"),
    SHARE_QUESTION_NEARBY("分享问题给附近人"),
    SHARE_QUESTION("分享问题"),
    SHARE_EXPERIENCE("分享经验"),
    REPORT_QUESTION("举报问题"),
    QUESTION_BEEN_VIEWED("问题被浏览"),
    QUESTION_BEEN_FOLLOWED("问题被关注"),
    QUESTION_BEEN_UP_VOTED("问题被赞"),
    QUESTION_BEEN_DOWN_VOTED("问题被踩"),

    REPORT_QUESTION_COMMENT("举报问题评论"),

    CREATE_ANSWER("创建回答"),
    REPORT_ANSWER("举报回答"),
    ANSWER_BEEN_FAVORITED("回答被收藏"),
    ANSWER_BEEN_UP_VOTED("回答被赞"),
    ANSWER_BEEN_DOWN_VOTED("回答被踩"),

    REPORT_ANSWER_COMMENT("举报回答评论");

    private String description;

    IncentiveReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
