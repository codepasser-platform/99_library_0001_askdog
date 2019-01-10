package com.askdog.web.api;

import com.askdog.model.data.IncentiveLog;
import com.askdog.model.entity.User;
import com.askdog.service.IncentiveLogService;
import com.askdog.service.NotificationService;
import com.askdog.service.UserService;
import com.askdog.service.bo.*;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.event.annotation.TriggerEvent;
import com.askdog.service.event.annotation.TriggerEvent.TriggerEventItem;
import com.askdog.web.api.helper.IncentiveHelper;
import com.askdog.web.api.vo.*;
import com.askdog.web.api.vo.common.GroupDateResult;
import com.askdog.web.api.vo.common.PageResult;
import com.askdog.web.api.vo.profile.PersonalInfo;
import com.askdog.web.api.vo.profile.PostedUserDetail;
import com.askdog.web.api.vo.profile.ProfileAnswer;
import com.askdog.web.api.vo.profile.ProfileQuestion;
import com.askdog.web.api.vo.profile.incentive.GroupDate;
import com.askdog.web.api.vo.profile.incentive.ProfileIncentiveLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.askdog.model.entity.inner.EventType.REGISTER_CONFIRM;
import static com.askdog.model.entity.inner.EventType.REGISTER_SEND;
import static com.askdog.model.entity.inner.IncentiveType.EXP;
import static com.askdog.model.entity.inner.IncentiveType.POINTS;
import static com.askdog.web.api.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static com.askdog.web.api.vo.UserStatus.ANONYMOUS;
import static com.askdog.web.api.vo.UserStatus.AUTHENTICATED;
import static com.askdog.web.api.vo.common.GroupDataResultHelper.group;
import static com.askdog.web.api.vo.common.PageResultHelper.rePage;
import static com.google.common.base.Strings.isNullOrEmpty;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api")
public class UserApi {

    @Autowired private UserService userService;
    @Autowired private IncentiveHelper incentiveHelper;
    @Autowired private IncentiveLogService incentiveLogService;
    @Autowired private NotificationService notificationService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/me")
    public UserSelf me(@AuthenticationPrincipal User user) throws ServiceException {
        UserDetail refreshedUser = userService.findDetail(user.getUuid());

        // TODO should tidy up the transient filed which binds to user entity.
        if (!isNullOrEmpty(user.getNickName())) {
            refreshedUser.setName(user.getNickName());
        }
        if (!isNullOrEmpty(user.getAvatarUrl())) {
            refreshedUser.setAvatar(user.getAvatarUrl());
        }

        UserSelf userSelf = new UserSelf().from(refreshedUser);
        userSelf.setNoticeCount(notificationService.findNoticeCount(user.getUuid()));
        return userSelf;
    }

    @RequestMapping("/me/status")
    public UserStatus status(@AuthenticationPrincipal Authentication authentication) {
        return authentication == null ? ANONYMOUS : AUTHENTICATED;
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/user", method = POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    @TriggerEvent(@TriggerEventItem(performerId = "returnValue.id", eventType = REGISTER_SEND))
    public UserSelf create(@Valid @RequestBody RegisterUser user) throws ServiceException {
        UserDetail savedUser = userService.create(user.convert());
        UserSelf userSelf = new UserSelf().from(savedUser);
        userSelf.setNoticeCount(notificationService.findNoticeCount(savedUser.getUuid()));
        return userSelf;
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/user/confirm", method = PUT)
    @TriggerEvent(@TriggerEventItem(performerId = "returnValue.id", eventType = REGISTER_CONFIRM))
    public UserSelf confirmRegistration(@RequestParam("token") String token) throws ServiceException {
        return new UserSelf().from(userService.confirmRegistration(token));
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/user/existence", method = HEAD)
    public ResponseEntity existsName(@RequestParam(value = "username", required = false) String userName,
                                     @RequestParam(value = "mail", required = false) String mail) {
        if (isNullOrEmpty(userName) && isNullOrEmpty(mail)) {
            return ResponseEntity.badRequest().build();
        }

        if ((!isNullOrEmpty(userName) && !userService.existsByName(userName))
                || (!isNullOrEmpty(mail) && !userService.existsByEmail(mail))) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/favorites", method = GET)
    public PageResult<AnswerFavorite> favorites(@AuthenticationPrincipal User user, @PageableDefault(sort = "time", direction = DESC) Pageable pageable) throws ServiceException {
        return rePage(userService.findFavorites(user.getUuid(), pageable), t -> new AnswerFavorite().from(t));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/follows", method = GET)
    public PageResult<QuestionFollow> follows(@AuthenticationPrincipal User user, @PageableDefault(sort = "time", direction = DESC) Pageable pageable) throws ServiceException {
        return rePage(userService.findFollows(user.getUuid(), pageable), t -> new QuestionFollow().from(t));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/views", method = GET)
    public PageResult<QuestionView> views(@AuthenticationPrincipal User user, @PageableDefault(sort = "time", direction = DESC) Pageable pageable) throws ServiceException {
        return rePage(userService.findViews(user.getUuid(), pageable), t -> new QuestionView().from(t));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/sharings", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public PageResult<Experience> sharings(@AuthenticationPrincipal User user, @PageableDefault(sort = "creationTime", direction = DESC) Pageable pageable) throws ServiceException {
        return rePage(userService.findSharings(user.getUuid(), pageable), t -> {
            Experience experience = new Experience().from(t);
            t.getAnswers().stream()
                    .filter(answerDetail -> answerDetail.getAnswerer().getUuid().equals(user.getUuid()))
                    .findFirst()
                    .ifPresent(answerDetail -> experience.setExperienceAnswer(new Experience.ExperienceAnswerDetail().from(answerDetail)));

            return experience;
        });
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/profile/personal_info", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public PersonalInfo profileBasic(@AuthenticationPrincipal User user) throws ServiceException {
        return new PersonalInfo().from(userService.findDetail(user.getUuid()));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/profile/personal_info", method = PUT, produces = APPLICATION_JSON_UTF8_VALUE)
    public PersonalInfo updateProfileBasic(@AuthenticationPrincipal User user, @Valid @RequestBody PostedUserDetail detail) throws ServiceException {
        return new PersonalInfo().from(userService.updateDetail(user.getUuid(), detail.convert()));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/profile/avatar", method = PUT, produces = APPLICATION_JSON_UTF8_VALUE)
    public void updateProfileAvatar(@AuthenticationPrincipal User user, @RequestParam("linkId") String linkId) throws ServiceException {
        userService.updateAvatar(user.getUuid(), linkId);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/profile/questions", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public PageResult<ProfileQuestion> profileQuestions(@AuthenticationPrincipal User user, @PageableDefault(sort = "creationTime", direction = DESC) Pageable pageable) throws ServiceException {
        return rePage(userService.findQuestions(user.getUuid(), pageable), t -> new ProfileQuestion().from(t));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/profile/answers", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public PageResult<ProfileAnswer> profileAnswers(@AuthenticationPrincipal User user, @PageableDefault(sort = "creationTime", direction = DESC) Pageable pageable) throws ServiceException {
        return rePage(userService.findAnswers(user.getUuid(), pageable), t -> new ProfileAnswer().from(t));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/profile/points", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public PageResult<GroupDateResult<ProfileIncentiveLog>> profilePoints(@AuthenticationPrincipal User user, @PageableDefault(sort = "createTime", direction = DESC) Pageable pageable) throws ServiceException {
        return build(incentiveLogService.findIncentiveLog(user.getUuid(), POINTS, pageable));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/me/profile/exp", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public PageResult<GroupDateResult<ProfileIncentiveLog>> profileExp(@AuthenticationPrincipal User user, @PageableDefault(sort = "createTime", direction = DESC) Pageable pageable) throws ServiceException {
        return build(incentiveLogService.findIncentiveLog(user.getUuid(), EXP, pageable));
    }

    private PageResult<GroupDateResult<ProfileIncentiveLog>> build(Page<IncentiveLog> incentiveRecordPage) throws ServiceException {
        PageResult<ProfileIncentiveLog> incentiveLogPageResult = rePage(incentiveRecordPage, incentiveLog -> incentiveHelper.convert(incentiveLog.getId()));

        List<GroupDateResult<ProfileIncentiveLog>> profileIncentiveLogList = group(incentiveLogPageResult.getResult(), incentiveLog -> {
            LocalDate date = incentiveLog.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return new GroupDate().setY(date.getYear()).setM(date.getMonth().getValue()).setD(date.getDayOfMonth());
        });

        return new PageResult<>(profileIncentiveLogList, incentiveLogPageResult.getTotal(), incentiveLogPageResult.isLast());
    }

}
