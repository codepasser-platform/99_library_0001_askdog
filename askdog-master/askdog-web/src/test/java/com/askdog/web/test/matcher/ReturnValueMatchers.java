package com.askdog.web.test.matcher;

import org.apache.commons.beanutils.PropertyUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Maps.immutableEntry;
import static java.lang.String.format;

public final class ReturnValueMatchers {

    public static <T> Matcher<T> describedAs(Object ... matchers) {
        checkArgument(matchers.length % 2 == 0, "arguments count not right, should be '(key, value)+' style");
        List<Map.Entry<String, Matcher>> entries = Stream.iterate(0, n -> n + 2).limit(matchers.length / 2)
                .map(i -> immutableEntry((String) matchers[i], (Matcher) matchers[i + 1]))
                .collect(Collectors.toList());

        return new TypeSafeDiagnosingMatcher<T>() {

            private List<Map.Entry<String, Matcher>> notMatched;

            @Override
            protected boolean matchesSafely(T item, Description mismatchDescription) {
                notMatched = entries.stream()
                        .filter(entry -> {
                            try {
                                String key = entry.getKey();
                                Matcher matcher = entry.getValue();
                                Object value = PropertyUtils.getNestedProperty(item, key);
                                if (matcher.matches(value)) {
                                   return false;
                                }

                                mismatchDescription.appendText(format("\n          \"%s\": ", key));
                                matcher.describeMismatch(value, mismatchDescription);
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                mismatchDescription.appendValue("          error occurs while asserting: " + e.getMessage());
                            }

                            return true;
                        })
                        .collect(Collectors.toList());

                return notMatched.isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                notMatched.forEach(entry -> {
                    description.appendText(format("\n          \"%s\": ", entry.getKey()));
                    entry.getValue().describeTo(description);
                });
            }

        };
    }

}
