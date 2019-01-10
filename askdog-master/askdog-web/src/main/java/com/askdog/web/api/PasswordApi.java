package com.askdog.web.api;

import com.askdog.model.entity.User;
import com.askdog.service.PasswordService;
import com.askdog.service.bo.ChangePassword;
import com.askdog.service.exception.ServiceException;
import com.askdog.web.api.vo.TokenPassword;
import com.askdog.web.api.vo.TokenValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/password")
public class PasswordApi {

    @Autowired
    private PasswordService passwordService;

    @PreAuthorize("permitAll")
    @RequestMapping(value = "/recover", method = POST)
    public void recoverPassword(@RequestParam("mail") String mailAddress) throws ServiceException {
        passwordService.sendRecoverPasswordMail(mailAddress);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(value = "/token/validation")
    public TokenValidation validatePasswordToken(@RequestParam("token") String token) {
        TokenValidation validation = new TokenValidation();
        validation.setToken(token);
        validation.setValid(passwordService.validatePasswordToken(token));
        return validation;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(method = PUT)
    public void updatePassword(@Valid @RequestBody TokenPassword tokenPassword) throws ServiceException {
        passwordService.updatePassword(tokenPassword.getToken(), tokenPassword.getMail(), tokenPassword.getPassword());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/change", method = PUT)
    public void updatePassword(@AuthenticationPrincipal User user, @Valid @RequestBody ChangePassword changePassword) throws ServiceException {
        passwordService.changePassword(user.getUuid(), changePassword);
    }

}
