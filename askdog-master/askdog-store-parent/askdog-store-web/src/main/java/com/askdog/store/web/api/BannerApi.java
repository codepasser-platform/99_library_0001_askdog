package com.askdog.store.web.api;

import com.askdog.store.service.BannerService;
import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.web.api.vo.PresentationBanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/banner")
public class BannerApi {

    @Autowired
    private BannerService bannerService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<PresentationBanner> findAll() throws ServiceException {
        return bannerService.findAll().stream().map((bannerDetail) -> new PresentationBanner().from(bannerDetail)).collect(Collectors.toList());
    }

}
