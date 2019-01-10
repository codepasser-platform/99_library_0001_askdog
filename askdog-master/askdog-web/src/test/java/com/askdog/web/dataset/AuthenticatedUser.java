package com.askdog.web.dataset;

import com.askdog.model.entity.User;
import com.askdog.model.repository.UserRepository;
import com.askdog.web.api.client.ApiClient;
import com.askdog.web.api.vo.RegisterUser;
import com.askdog.web.api.vo.UserSelf;
import com.askdog.web.test.DataSet;
import com.askdog.web.test.DefaultCookieStore;
import com.askdog.web.test.cleanup.CleanupExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.CookieStore;

import static com.askdog.web.dataset.RegisteredUser.PASSWORD;

public class AuthenticatedUser implements DataSet {

    private static final ThreadLocal<DataHolder> dataHolder = new ThreadLocal<>();

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void prepare() {
        DataHolder holder = new DataHolder();
        dataHolder.set(holder);
        RegisterUser registerUser = RegisteredUser.anyRegisteredUser();

        holder.userSelf = apiClient.authenticateUserApi()
                .withCookies(holder.cookieStore)
                .authenticate(registerUser.getName(), PASSWORD)
                .getBody();
        holder.user = userRepository.findByName(registerUser.getName()).orElse(null);
    }

    @Override
    public CleanupExecutor cleanupExecutor() {
        return dataHolder::remove;
    }

    public static CookieStore getCookieStore() {
        return dataHolder.get().cookieStore;
    }

    public static UserSelf getAuthenticatedUserSelf() {
        return dataHolder.get().userSelf;
    }

    public static User getAuthenticatedUser() {
        return dataHolder.get().user;
    }

    private static class DataHolder {
        UserSelf userSelf;
        User user;
        CookieStore cookieStore = new DefaultCookieStore();
    }
}
