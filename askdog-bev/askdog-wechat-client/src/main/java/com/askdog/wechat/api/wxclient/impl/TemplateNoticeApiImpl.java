package com.askdog.wechat.api.wxclient.impl;

import com.askdog.wechat.api.ApiRequest;
import com.askdog.wechat.api.wxclient.TemplateNoticeApi;
import com.askdog.wechat.api.wxclient.exception.WxClientException;
import com.askdog.wechat.api.wxclient.model.TemplateNotice;
import com.askdog.wechat.api.wxclient.model.WxResult;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.askdog.common.utils.Variables.variables;
import static com.askdog.wechat.api.ApiRequestBuilder.buildPostRequest;
import static com.google.common.collect.ImmutableMap.of;

class TemplateNoticeApiImpl implements TemplateNoticeApi {

    private static final String API_PATH = "/message/template/send";

    private URI uri;

    private String templateId;

    private String topColor;

    private String link;

    TemplateNoticeApiImpl(String accessToken, String baseUrl, String templateId, String topColor, String link) {
        uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path(API_PATH)
                .queryParam("access_token", accessToken)
                .build()
                .toUri();
        this.templateId = templateId;
        this.topColor = topColor;
        this.link = link;
    }

    @Override
    public WxResult request(TemplateNotice body) throws WxClientException {
        body.setTemplateId(this.templateId);
        body.setTopColor(this.topColor);
        body.setUrl(variables(of("targetId", body.getTargetId())).pattern("\\{(\\w+)\\}").replace(this.link));
        ApiRequest.ApiRequestWithBody<WxResult, TemplateNotice> apiExchange = buildPostRequest(uri, WxResult.class);
        return apiExchange.request(body);
    }
}
