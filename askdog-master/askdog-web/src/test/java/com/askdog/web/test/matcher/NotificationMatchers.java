package com.askdog.web.test.matcher;

import com.askdog.common.utils.Json;
import com.askdog.model.data.OriginalNotification;
import com.askdog.model.repository.mongo.OriginalNotificationRepository;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public final class NotificationMatchers {

    public static  <T> Matcher<T> notified(OriginalNotification originalNotification) {

        return new TypeSafeDiagnosingMatcher<T>() {

            @Autowired
            private OriginalNotificationRepository originalNotificationRepository;

            @Override
            protected boolean matchesSafely(T item, Description mismatchDescription) {
                boolean found = originalNotificationRepository.findAll().stream().anyMatch(original ->
                        original.getNotificationType() == originalNotification.getNotificationType()
                                // && Objects.equals(original.getRecipientUser(), originalNotification.getRecipientUser())
                                // && Objects.equals(original.getTarget(), originalNotification.getTarget())
                                && original.isRead() == originalNotification.isRead()
                );

                if (!found) {
                    mismatchDescription.appendText("not found the notification which matched the expected notification");
                }

                return found;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("the notification should be found. \n          ");
                description.appendText(Json.writeValueAsString(originalNotification));
            }
        };
    }

}
