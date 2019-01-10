package com.askdog.web.api;

import com.askdog.model.data.inner.location.LocationDescription;
import com.askdog.service.LocationService;
import com.askdog.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.askdog.web.api.utils.HeaderUtils.getRequestRealIp;

@RestController
@RequestMapping("/api/location")
public class LocationApi {

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/geo", method = RequestMethod.GET)
    public LocationDescription[] geo(@RequestParam(value = "lat", required = false) Double lat, @RequestParam(value = "lng", required = false) Double lng, HttpServletRequest request) throws ServiceException {
        return locationService.suggestion(getRequestRealIp(request), lat, lng);
    }

}
