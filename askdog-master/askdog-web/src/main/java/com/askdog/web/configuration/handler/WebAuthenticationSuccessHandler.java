package com.askdog.web.configuration.handler;

import com.askdog.model.entity.User;
import com.askdog.service.LocationService;
import com.askdog.service.NotificationService;
import com.askdog.service.UserService;
import com.askdog.service.bo.UserDetail;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.event.ListenerRegistry;
import com.askdog.service.event.core.EventBuilder;
import com.askdog.web.HttpEntityProcessor;
import com.askdog.web.api.vo.UserSelf;
import com.askdog.web.configuration.userdetails.AdUserDetails;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.askdog.model.data.inner.TargetType.USER;
import static com.askdog.model.entity.inner.EventType.LOGIN;
import static com.askdog.web.api.MediaType.APPLICATION_JSON_UTF8;
import static com.askdog.web.api.utils.HeaderUtils.getRequestRealIp;
import static org.springframework.http.HttpStatus.OK;

public class WebAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AjaxAuthenticationHandler<WebAuthenticationSuccessHandler> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebAuthenticationSuccessHandler.class);

    @Autowired private UserService userService;
    @Autowired private HttpEntityProcessor httpEntityProcessor;
    @Autowired private ListenerRegistry listenerRegistry;
    @Autowired private LocationService locationService;
    @Autowired private NotificationService notificationService;

    private String ajaxParam = DEFAULT_AJAX_PARAM;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        try {
            // update the last access time.
            AdUserDetails userDetails = (AdUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            user.setLastAccessTime(new Date());
            userService.save(user);

            // user location
            location(request, user.getUuid());

            listenerRegistry.fire(EventBuilder.getBuilder()
                    .setPerformerId(user.getUuid())
                    .setEventType(LOGIN)
                    .build());

            UserDetail refreshedUser = userService.findDetail(userDetails.getUser().getUuid());

            // write user info into response
            UserSelf userSelf = new UserSelf().from(refreshedUser);
            userSelf.setNoticeCount(notificationService.findNoticeCount(user.getUuid()));

            // redirect previous url
            if (!Boolean.valueOf(request.getParameter(ajaxParam))) {
                super.onAuthenticationSuccess(request, response, authentication);
            }

            ResponseEntity<UserSelf> entity = ResponseEntity.status(OK)
                    .contentType(APPLICATION_JSON_UTF8)
                    .body(userSelf);
            httpEntityProcessor.writeEntity(entity, response);

        } catch (ServiceException e) {
            throw new ServletException("Login error.", e);
        }
    }


    public WebAuthenticationSuccessHandler ajaxParam(String name) {
        this.ajaxParam = name;
        return this;
    }

    public WebAuthenticationSuccessHandler targetUrlParam(String name) {
        super.setTargetUrlParameter(name);
        return this;
    }

    private void location(HttpServletRequest request, String userId) {
        try {
            String latParam = request.getParameter(GEO_LAT_PARAM);
            String lngParam = request.getParameter(GEO_LNG_PARAM);
            try {
                Double lat = Strings.isNullOrEmpty(latParam) ? null : Double.valueOf(latParam);
                Double lng = Strings.isNullOrEmpty(lngParam) ? null : Double.valueOf(lngParam);
                locationService.analysis(USER, userId, getRequestRealIp(request), lat, lng);
            } catch (NumberFormatException e) {
                locationService.analysis(USER, userId, getRequestRealIp(request), null, null);
            }

            // user residence
            locationService.residence(userId);
        } catch (ServiceException e) {
            LOGGER.warn(e.getLocalizedMessage());
        }
    }

}
