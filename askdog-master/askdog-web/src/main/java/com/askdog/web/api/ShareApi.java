package com.askdog.web.api;

import com.askdog.service.exception.ServiceException;
import com.askdog.web.api.vo.ShareConfiguration;
import com.askdog.web.api.vo.ShareType;
import com.askdog.web.configuration.oauth2.clients.DefaultClientResources;
import com.askdog.web.configuration.oauth2.clients.QQClientResources;
import com.askdog.web.configuration.oauth2.clients.WeiboClientResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumMap;
import java.util.Map;

@RestController
@RequestMapping("/api/share")
public class ShareApi {

    @Autowired
    @Qualifier("github")
    private DefaultClientResources githubClientResources;

    @Autowired
    private WeiboClientResources weiboClientResources;

    @Autowired
    private QQClientResources qqClientResources;

    @RequestMapping(value = "/configurations", method = RequestMethod.GET)
    public Map<ShareType, ShareConfiguration> getShareConfigurations() throws ServiceException {
        EnumMap<ShareType, ShareConfiguration> configurationEnumMap = new EnumMap<>(ShareType.class);
        configurationEnumMap.put(ShareType.WEIBO, new ShareConfiguration().from(weiboClientResources));
        configurationEnumMap.put(ShareType.QQ, new ShareConfiguration().from(qqClientResources));
        return configurationEnumMap;
    }

}
