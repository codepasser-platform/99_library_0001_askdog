package com.askdog.service.impl;

import com.askdog.model.entity.User;
import com.askdog.model.repository.UserRepository;
import com.askdog.service.PasswordService;
import com.askdog.service.bo.ChangePassword;
import com.askdog.service.exception.IllegalArgumentException;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.mail.PasswordTokenMail;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.askdog.common.RegexPattern.REGEX_MAIL;
import static com.askdog.model.entity.inner.user.UserStatus.MAIL_CONFIRMED;
import static com.askdog.service.exception.IllegalArgumentException.Error.INVALID_ORIGIN_PASSWORD;
import static com.askdog.service.exception.NotFoundException.Error.USER;
import static com.google.common.base.Preconditions.checkArgument;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordTokenMail passwordTokenMail;

    @Override
    public void sendRecoverPasswordMail(@Nonnull String mail) throws ServiceException {
        checkArgument(mail.matches(REGEX_MAIL), "invalid mail address");
        Optional<User> foundUser = userRepository.findByEmail(mail);
        if (!foundUser.isPresent()) {
            throw new NotFoundException(USER);
        }

        passwordTokenMail.send(foundUser.get());
    }

    @Override
    public boolean validatePasswordToken(@Nonnull String token) {
        return passwordTokenMail.isTokenValidate(token);
    }

    @Override
    public void updatePassword(@Nonnull String token, @Nonnull String mail, @Nonnull String newPassword) throws ServiceException {
        passwordTokenMail.redeemToken(mail, token);
        User user = userRepository.findByEmail(mail).orElseThrow(() -> new NotFoundException(USER));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.addStatus(MAIL_CONFIRMED);
        userRepository.save(user);
    }

    @Override
    public void changePassword(String userId, ChangePassword changePassword) throws ServiceException {
        User user = userRepository.findByUuid(userId).orElseThrow(() -> new NotFoundException(USER));

        if (!Strings.isEmpty(user.getPassword()) && !passwordEncoder.matches(changePassword.getOriginPassword(), user.getPassword())) {
            throw new IllegalArgumentException(INVALID_ORIGIN_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userRepository.save(user);
    }
}
