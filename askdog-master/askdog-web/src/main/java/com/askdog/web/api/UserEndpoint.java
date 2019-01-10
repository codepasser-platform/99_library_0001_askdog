package com.askdog.web.api;

import com.askdog.model.entity.User;
import com.askdog.service.UserService;
import com.askdog.service.bo.UserDetail;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import com.askdog.web.api.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.askdog.service.exception.NotFoundException.Error.USER;

@RestController
@RequestMapping("/oauth")
public class UserEndpoint {

    @Autowired
    private UserService userService;

    @RequestMapping("/userinfo")
    @ResponseBody
    public UserInfo me(Principal user) throws ServiceException {
        return new UserInfo().from((User) ((OAuth2Authentication) user).getPrincipal());
    }

    // TODO: 16-7-14  Request details from store client
    @RequestMapping(value = "/userinfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserDetail detail(@PathVariable String id) throws ServiceException {
        return new UserDetail().from(userService.findById(id).orElseThrow(() -> new NotFoundException(USER)));
    }


    @RequestMapping(value = "/userinfo/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public UserDetail deduction(@PathVariable String id, @RequestParam("points") int points) throws ServiceException {
        return userService.deductionPoints(id, points);
    }
}
