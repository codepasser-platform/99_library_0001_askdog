package com.askdog.store.web.api;

import com.askdog.store.model.data.Area;
import com.askdog.store.service.LocationService;
import com.askdog.store.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.askdog.store.model.data.inner.area.AreaTree.AreaLevel.valueOf;


@RestController
@RequestMapping("/api/location")
public class LocationApi {

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/area", method = RequestMethod.GET)
    @ResponseBody
    public List<Area> areas(@RequestParam("level") int level, @RequestParam(value = "parentId", required = false) String parentId) throws ServiceException {
        return locationService.areas(valueOf(level), parentId);
    }

}
