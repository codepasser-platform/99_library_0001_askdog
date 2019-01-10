package com.askdog.web.api;

import com.askdog.model.data.OriginalNotification;
import com.askdog.model.entity.User;
import com.askdog.service.NotificationService;
import com.askdog.service.exception.ServiceException;
import com.askdog.web.api.helper.NotificationHelper;
import com.askdog.web.api.vo.Notification;
import com.askdog.web.api.vo.common.GroupDateResult;
import com.askdog.web.api.vo.common.PageResult;
import com.askdog.web.api.vo.profile.incentive.GroupDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.askdog.web.api.vo.common.GroupDataResultHelper.group;
import static com.askdog.web.api.vo.common.PageResultHelper.rePage;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/notifications")
public class NotificationApi {

    private static final Logger logger = LoggerFactory.getLogger(NotificationApi.class);

    @Autowired private NotificationService notificationService;
    @Autowired private NotificationHelper notificationHelper;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = GET)
    public PageResult<GroupDateResult<Notification>> findAll(@AuthenticationPrincipal User user, @PageableDefault(sort = "createTime", direction = DESC) Pageable pageable) throws ServiceException {
        return build(notificationService.findAll(user.getUuid(), pageable));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/preview", method = GET)
    public List<Notification> findPreviewNotifications(@AuthenticationPrincipal User user) throws ServiceException {
        return notificationService.findPreviewNotifications(user.getUuid()).stream()
                .map(this::quietConvert)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/read_all", method = PUT)
    public void readAll(@AuthenticationPrincipal User user) {
        notificationService.readAll(user.getUuid());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/read", method = PUT)
    public void markAsRead(@PathVariable("id") String notificationId) throws ServiceException {
        notificationService.markAsRead(notificationId);
    }

    private PageResult<GroupDateResult<Notification>> build(Page<OriginalNotification> notifications) throws ServiceException {
        PageResult<Notification> notificationPageResult = rePage(notifications, t -> notificationHelper.convert(t));

        List<GroupDateResult<Notification>> groupDateResults = group(notificationPageResult.getResult(), notification -> {
            LocalDate date = notification.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return new GroupDate().setY(date.getYear()).setM(date.getMonth().getValue()).setD(date.getDayOfMonth());
        });

        return new PageResult<>(groupDateResults, notificationPageResult.getTotal(), notificationPageResult.isLast());
    }

    private Notification quietConvert(OriginalNotification originalNotification) {
        try {
            return notificationHelper.convert(originalNotification);
        } catch (ServiceException e) {
            logger.error("can not convert the OriginalNotification to Notification", e);
        }

        return null;
    }

}
