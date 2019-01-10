package com.askdog.store.web.configuration.handler;

import com.askdog.store.model.entity.Buyer;
import com.askdog.store.service.BuyerService;
import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.web.HttpEntityProcessor;
import com.askdog.store.web.api.vo.BuyerSelf;
import com.askdog.store.web.configuration.userdetails.AdBuyerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.askdog.store.web.api.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.HttpStatus.OK;

public class WebAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AjaxAuthenticationHandler<WebAuthenticationSuccessHandler> {

    @Autowired
    private HttpEntityProcessor httpEntityProcessor;

    private String ajaxParam = DEFAULT_AJAX_PARAM;

    @Autowired
    private BuyerService buyerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        AdBuyerDetails buyerDetails = (AdBuyerDetails) authentication.getPrincipal();

        // sync user info
        syncUser(buyerDetails);

        // redirect previous url
        if (!Boolean.valueOf(request.getParameter(ajaxParam))) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        // write buyer info into response
        BuyerSelf buyerSelf = new BuyerSelf().from(buyerDetails.getBuyer());

        ResponseEntity<BuyerSelf> entity = ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON_UTF8)
                .body(buyerSelf);

        httpEntityProcessor.writeEntity(entity, response);
    }

    private void syncUser(AdBuyerDetails buyerDetails) {
        try {
            Buyer buyer = buyerService.save(buyerDetails.getBuyer());
            buyerDetails.setId(buyer.getUuid());
        } catch (ServiceException e) {
            // ignore
            e.printStackTrace();
        }
    }

    public WebAuthenticationSuccessHandler targetUrlParam(String name) {
        super.setTargetUrlParameter(name);
        return this;
    }

    @Override
    public WebAuthenticationSuccessHandler ajaxParam(String name) {
        this.ajaxParam = name;
        return this;
    }


}
