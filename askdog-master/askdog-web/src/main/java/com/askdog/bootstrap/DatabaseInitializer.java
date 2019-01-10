package com.askdog.bootstrap;

import com.askdog.model.entity.inner.EventType;
import com.askdog.model.entity.inner.ExpressionType;
import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.askdog.common.RunningProfile.INITIALIZE;
import static com.askdog.model.entity.builder.IncentivePolicyBuilder.getBuilder;
import static com.askdog.model.entity.builder.TemplateBuilder.mailTemplateBuilder;
import static com.google.common.base.Joiner.on;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.SIMPLIFIED_CHINESE;

@Service
@Profile(INITIALIZE)
public class DatabaseInitializer {

    @Autowired
    private DataCreator dataCreator;

    @PostConstruct
    private void initialize() {
        dataCreator
                .template(
                        mailTemplateBuilder()
                                .name("registration-confirm")
                                .subject("[Ask.Dog] Confirm your account registration")
                                .content(on("\n").join(
                                        "Hello, ${user.name}:",
                                        "",
                                        "To confirm that this user account really belongs to you, please click on this URL or copy/paste it to your browser:",
                                        "${url}",
                                        "",
                                        "Ask.Dog",
                                        "${.now?string[\"yyyy-MM-dd, HH:mm\"]}"
                                ))
                                .language(ENGLISH)
                                .build()
                )
                .template(
                        mailTemplateBuilder()
                                .name("registration-confirm")
                                .subject("[Ask.Dog] 注册确认")
                                .content(on("\n").join(
                                        "${user.name}, 您好:",
                                        "",
                                        "我们已经收到了您的注册申请，请点击以下链接完成Ask.Dog账号的注册：",
                                        "${url}",
                                        "(如果链接不能点击，请复制并粘贴到浏览器的地址栏，然后按回车键)",
                                        "",
                                        "Ask.Dog",
                                        "${.now?string[\"yyyy-MM-dd, HH:mm\"]}"
                                ))
                                .language(SIMPLIFIED_CHINESE)
                                .build()
                )
                .template(
                        mailTemplateBuilder()
                                .name("password-recover")
                                .subject("[Ask.Dog] Find your password")
                                .content(on("\n").join(
                                        "Hello, ${user.name}:",
                                        "",
                                        "You are trying to recover your password, if you not forget your password, please ignore this email.",
                                        "To continue, please click on this URL or copy/paste it to your browser:",
                                        "${url}",
                                        "",
                                        "Ask.Dog",
                                        "${.now?string[\"yyyy-MM-dd, HH:mm\"]}"
                                ))
                                .language(ENGLISH)
                                .build()
                )
                .template(
                        mailTemplateBuilder()
                                .name("password-recover")
                                .subject("[Ask.Dog] 找回密码")
                                .content(on("\n").join(
                                        "${user.name}, 您好:",
                                        "",
                                        "您正在试图通过邮箱找回您的密码，如果不是您本人操作，请忽略。",
                                        "继续找回您的密码，请点击下面的链接重置。",
                                        "${url}",
                                        "(如果链接不能点击，请复制并粘贴到浏览器的地址栏，然后按回车键)",
                                        "",
                                        "Ask.Dog",
                                        "${.now?string[\"yyyy-MM-dd, HH:mm\"]}"
                                ))
                                .language(SIMPLIFIED_CHINESE)
                                .build()
                )
                .template(
                        mailTemplateBuilder()
                                .name("notification-question-answer")
                                .subject("[Ask.Dog] Your question has a new answer")
                                .content(on("\n").join(
                                        "Hello, ${user.name}:",
                                        "",
                                        "You asked a question \"${question.subject}\" in Ask.Dog, and you got a new answer, please click on this URL or copy/paste it to your browser:",
                                        "${url}",
                                        "",
                                        "Ask.Dog",
                                        "${.now?string[\"yyyy-MM-dd, HH:mm\"]}"
                                ))
                                .language(ENGLISH)
                                .build()
                )
                .template(
                        mailTemplateBuilder()
                                .name("notification-question-answer")
                                .subject("[Ask.Dog] 您的提问有了新的回答")
                                .content(on("\n").join(
                                        "${user.name}, 您好:",
                                        "",
                                        "您在Ask.Dog上的提问\"${question.subject}\"有了新的回答，请点击下面的连接查看。",
                                        "${url}",
                                        "(如果链接不能点击，请复制并粘贴到浏览器的地址栏，然后按回车键)",
                                        "",
                                        "Ask.Dog",
                                        "${.now?string[\"yyyy-MM-dd, HH:mm\"]}"
                                ))
                                .language(SIMPLIFIED_CHINESE)
                                .build()
                )
                .template(
                        mailTemplateBuilder()
                                .name("notification-answer-followed-question")
                                .subject("[Ask.Dog] Your followed question has a new answer")
                                .content(on("\n").join(
                                        "Hello, ${user.name}:",
                                        "",
                                        "You followed a question \"${question.subject}\" in Ask.Dog, and this question got a new answer, please click on this URL or copy/paste it to your browser:",
                                        "${url}",
                                        "",
                                        "Ask.Dog",
                                        "${.now?string[\"yyyy-MM-dd, HH:mm\"]}"
                                ))
                                .language(ENGLISH)
                                .build()
                )
                .template(
                        mailTemplateBuilder()
                                .name("notification-answer-followed-question")
                                .subject("[Ask.Dog] 您关注的提问有了新的回答")
                                .content(on("\n").join(
                                        "${user.name}, 您好:",
                                        "",
                                        "您在Ask.Dog上的关注的提问\"${question.subject}\"有了新的回答，请点击下面的连接查看。",
                                        "${url}",
                                        "(如果链接不能点击，请复制并粘贴到浏览器的地址栏，然后按回车键)",
                                        "",
                                        "Ask.Dog",
                                        "${.now?string[\"yyyy-MM-dd, HH:mm\"]}"
                                ))
                                .language(SIMPLIFIED_CHINESE)
                                .build()
                );

        // TODO using EnumSet instead !
        dataCreator
                .incentivePolicy(getBuilder()
                        .setName("用户注册积分奖励")
                        .setEventType(EventType.REGISTER_CONFIRM)
                        .setIncentiveReason(IncentiveReason.REGISTER_CONFIRM)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.TEXT)
                        .setExpression("2")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("用户登录积分奖励")
                        .setEventType(EventType.LOGIN)
                        .setIncentiveReason(IncentiveReason.LOGIN)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.TEXT)
                        .setExpression("1")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("用户提问积分奖励")
                        .setEventType(EventType.CREATE_QUESTION)
                        .setIncentiveReason(IncentiveReason.CREATE_QUESTION)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.TEXT)
                        .setExpression("2")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("用户回答积分奖励")
                        .setEventType(EventType.CREATE_ANSWER)
                        .setIncentiveReason(IncentiveReason.CREATE_ANSWER)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.TEXT)
                        .setExpression("2")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("用户分享经验积分奖励")
                        .setEventType(EventType.SHARE_EXPERIENCE)
                        .setIncentiveReason(IncentiveReason.SHARE_EXPERIENCE)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.TEXT)
                        .setExpression("3")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("用户分享问题给附近人积分奖励")
                        .setEventType(EventType.SHARE_QUESTION_NEARBY)
                        .setIncentiveReason(IncentiveReason.SHARE_QUESTION_NEARBY)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.TEXT)
                        .setExpression("1")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("用户分享问题")
                        .setEventType(EventType.SHARE_QUESTION)
                        .setIncentiveReason(IncentiveReason.SHARE_QUESTION)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.TEXT)
                        .setExpression("5")
                        .build())
//                .incentivePolicy(getBuilder()
//                        .setName("用户举报问题积分奖励")
//                        .setEventType(EventType.REPORT_QUESTION)
//                        .setIncentiveReason(IncentiveReason.REPORT_QUESTION)
//                        .setIncentiveType(IncentiveType.POINTS)
//                        .setExpressionType(ExpressionType.TEXT)
//                        .setExpression("10")
//                        .build())
//                .incentivePolicy(getBuilder()
//                        .setName("用户举报回答积分奖励")
//                        .setEventType(EventType.REPORT_ANSWER)
//                        .setIncentiveReason(IncentiveReason.REPORT_ANSWER)
//                        .setIncentiveType(IncentiveType.POINTS)
//                        .setExpressionType(ExpressionType.TEXT)
//                        .setExpression("100")
//                        .build())
//                .incentivePolicy(getBuilder()
//                        .setName("用户举报问题评论积分奖励")
//                        .setEventType(EventType.REPORT_QUESTION_COMMENT)
//                        .setIncentiveReason(IncentiveReason.REPORT_QUESTION_COMMENT)
//                        .setIncentiveType(IncentiveType.POINTS)
//                        .setExpressionType(ExpressionType.TEXT)
//                        .setExpression("100")
//                        .build())
//                .incentivePolicy(getBuilder()
//                        .setName("用户举报回答评论积分奖励")
//                        .setEventType(EventType.REPORT_ANSWER_COMMENT)
//                        .setIncentiveReason(IncentiveReason.REPORT_ANSWER_COMMENT)
//                        .setIncentiveType(IncentiveType.POINTS)
//                        .setExpressionType(ExpressionType.TEXT)
//                        .setExpression("100")
//                        .build())
                .incentivePolicy(getBuilder()
                        .setName("用户完善信息积分奖励")
                        .setEventType(EventType.PERFECT_PERSONAL_INFORMATION)
                        .setIncentiveReason(IncentiveReason.PERFECT_PERSONAL_INFORMATION)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.TEXT)
                        .setExpression("4")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("用户反馈问题初始积分奖励")
                        .setEventType(EventType.FEEDBACK_SEND)  // TODO unused
                        .setIncentiveReason(IncentiveReason.FEEDBACK_SEND)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.TEXT)
                        .setExpression("2")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("用户反馈问题通过积分奖励")
                        .setEventType(EventType.FEEDBACK_ACCEPT)  // TODO unused
                        .setIncentiveReason(IncentiveReason.FEEDBACK_ACCEPT)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.TEXT)
                        .setExpression("4")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("问题被关注积分奖励")
                        .setEventType(EventType.FOLLOW_QUESTION)
                        .setIncentiveReason(IncentiveReason.QUESTION_BEEN_FOLLOWED)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("question_follow_count%5 == 0 ? 1 : 0")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("问题被浏览积分奖励")
                        .setEventType(EventType.VIEW_QUESTION)
                        .setIncentiveReason(IncentiveReason.QUESTION_BEEN_VIEWED)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("question_view_count%100 == 0 ? 1 : 0")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("问题被赞积分奖励")
                        .setEventType(EventType.UP_VOTE_QUESTION)
                        .setIncentiveReason(IncentiveReason.QUESTION_BEEN_UP_VOTED)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("question_up_vote_count%10 == 0 ? 1 : 0")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("回答被赞积分奖励")
                        .setEventType(EventType.UP_VOTE_ANSWER)
                        .setIncentiveReason(IncentiveReason.ANSWER_BEEN_UP_VOTED)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("answer_up_vote_count%10 == 0 ? 1 : 0")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("回答被收藏积分奖励")
                        .setEventType(EventType.FAV_ANSWER)
                        .setIncentiveReason(IncentiveReason.ANSWER_BEEN_FAVORITED)
                        .setIncentiveType(IncentiveType.POINTS)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("answer_fav_count%5 == 0 ? 1 : 0")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("问题被关注经验奖励")
                        .setEventType(EventType.FOLLOW_QUESTION)
                        .setIncentiveReason(IncentiveReason.QUESTION_BEEN_FOLLOWED)
                        .setIncentiveType(IncentiveType.EXP)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("1")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("问题被浏览经验奖励")
                        .setEventType(EventType.VIEW_QUESTION)
                        .setIncentiveReason(IncentiveReason.QUESTION_BEEN_VIEWED)
                        .setIncentiveType(IncentiveType.EXP)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("question_view_count%100 == 0 ? 1 : 0")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("问题被赞经验奖励")
                        .setEventType(EventType.UP_VOTE_QUESTION)
                        .setIncentiveReason(IncentiveReason.QUESTION_BEEN_UP_VOTED)
                        .setIncentiveType(IncentiveType.EXP)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("question_up_vote_count%10 == 0 ? 1 : 0")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("问题被踩经验惩罚")
                        .setEventType(EventType.DOWN_VOTE_QUESTION)
                        .setIncentiveReason(IncentiveReason.QUESTION_BEEN_DOWN_VOTED)
                        .setIncentiveType(IncentiveType.EXP)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("question_down_vote_count%10 == 0 ? -1 : 0")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("回答被赞经验奖励")
                        .setEventType(EventType.UP_VOTE_ANSWER)
                        .setIncentiveReason(IncentiveReason.ANSWER_BEEN_UP_VOTED)
                        .setIncentiveType(IncentiveType.EXP)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("answer_up_vote_count%10 == 0 ? 1 : 0")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("回答被踩经验惩罚")
                        .setEventType(EventType.DOWN_VOTE_ANSWER)
                        .setIncentiveReason(IncentiveReason.ANSWER_BEEN_DOWN_VOTED)
                        .setIncentiveType(IncentiveType.EXP)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("answer_down_vote_count%10 == 0 ? -1 : 0")
                        .build())
                .incentivePolicy(getBuilder()
                        .setName("回答被收藏经验奖励")
                        .setEventType(EventType.FAV_ANSWER)
                        .setIncentiveReason(IncentiveReason.ANSWER_BEEN_FAVORITED)
                        .setIncentiveType(IncentiveType.EXP)
                        .setExpressionType(ExpressionType.JAVASCRIPT)
                        .setExpression("1")
                        .build());

    }

}
