package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.web.configuration.oauth2.clients.DefaultClientResources;
import org.springframework.util.Assert;

public class ShareConfiguration implements Out<ShareConfiguration, DefaultClientResources> {

    private String clientId;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public ShareConfiguration from(DefaultClientResources clientResources) {
        Assert.notNull(clientResources);
        this.clientId = clientResources.getClient().getClientId();
        return this;
    }

}
