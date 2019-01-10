package com.askdog.web.test;

import com.askdog.model.entity.User;
import com.askdog.web.api.vo.RegisterUser;
import com.askdog.web.api.vo.UserSelf;
import com.askdog.web.dsl.Chain;
import com.askdog.web.dsl.Injector.CookieInjector;
import com.askdog.web.test.Inspector.ComposedInspector;
import com.askdog.web.test.Inspector.StatusInspector;

public interface UserTest {

    interface Test extends Api, CookieInjector<Api> {}

    interface Api extends Chain {
        UserInspector<UserSelf> me();
        UserInspector<Void> create(RegisterUser user);
        UserInspector<User> authenticate(String username, String password);
        UserInspector<Void> confirm(String token);
    }

    interface UserInspector<T> extends StatusInspector<ComposedInspector<T>>, ComposedInspector<T> {}
}
