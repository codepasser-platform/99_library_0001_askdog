package com.askdog.service;

import com.askdog.service.bo.ChangePassword;
import com.askdog.service.exception.ServiceException;

import javax.annotation.Nonnull;

public interface PasswordService {
    void sendRecoverPasswordMail(@Nonnull String mail) throws ServiceException;
    boolean validatePasswordToken(@Nonnull String token);
    void updatePassword(@Nonnull String token, @Nonnull String mail, @Nonnull String newPassword) throws ServiceException;

    void changePassword(String userId, ChangePassword changePassword) throws ServiceException;
}
