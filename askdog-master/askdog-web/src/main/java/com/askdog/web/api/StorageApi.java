package com.askdog.web.api;

import com.askdog.model.data.inner.ResourceType;
import com.askdog.service.StorageService;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.storage.AccessToken;
import com.askdog.service.storage.StorageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.askdog.common.utils.Json.writeValueAsString;
import static com.askdog.web.api.utils.AliyunOSSUtils.verifyOSSCallbackRequest;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.valueOf;

@RestController
@RequestMapping("/api/storage/")
public class StorageApi {

    private static final String LINK_ID_KEY = "linkId";

    @Autowired
    private StorageService storageService;

    @RequestMapping("access_token")
    public AccessToken accessToken(@RequestParam("type") ResourceType type, @RequestParam("filename") String fileName) throws ServiceException {
        return storageService.generateAccessToken(type, fileName);
    }

    @RequestMapping(value = "callback", method = RequestMethod.POST)
    public StorageResource callback(HttpServletRequest request, @RequestBody Map<String, Object> map) throws UnsupportedEncodingException, ServiceException {
        checkState(verifyOSSCallbackRequest(request, writeValueAsString(map)));
        return storageService.persistLink(valueOf(map.get(LINK_ID_KEY)));
    }

}