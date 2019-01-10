package com.askdog.service.proxy;

import com.askdog.service.PasswordService;
import com.askdog.service.bo.ChangePassword;
import com.askdog.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Override
    public void sendRecoverPasswordMail(@Nonnull String mail) throws ServiceException {

    }

    @Override
    public boolean validatePasswordToken(@Nonnull String token) {
        return false;
    }

    @Override
    public void updatePassword(@Nonnull String token, @Nonnull String mail, @Nonnull String newPassword) throws ServiceException {

    }

    @Override
    public void changePassword(String userId, ChangePassword changePassword) throws ServiceException {

    }
}
