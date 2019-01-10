package com.askdog.web.test.matcher;

import com.askdog.model.entity.StorageLink;
import com.askdog.model.entity.StorageLink.Status;
import com.askdog.model.repository.StorageLinkRepository;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static java.util.Arrays.asList;

public final class EntityMatchers {

    public static  <T> Matcher<T> picturesInStatus(Status state, String ... pictures) {

        return new TypeSafeDiagnosingMatcher<T>() {

            @Autowired
            private StorageLinkRepository storageLinkRepository;

            @Override
            protected boolean matchesSafely(T item, Description mismatchDescription) {
                List<StorageLink> foundPictures = newArrayList(storageLinkRepository.findAll(asList(pictures)));
                if (foundPictures.size() == pictures.length) {
                    List<StorageLink> mismatched = foundPictures.stream().filter(record -> record.getStatus() == state).collect(Collectors.toList());
                    if (mismatched.isEmpty()) {
                        return true;
                    }

                    mismatched.forEach(record -> mismatchDescription.appendText(record.getUuid()).appendText(" is ").appendValue(record.getStatus()));
                }

                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(format("pictures in status %s", state));
            }
        };
    }

}
