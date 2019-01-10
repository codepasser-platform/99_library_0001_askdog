package com.askdog.store.web.configuration.userdetails;

import com.askdog.store.model.entity.Buyer;
import com.askdog.store.model.repository.BuyerRepository;
import com.askdog.store.service.bo.BuyerDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class AdBuyerDetailsService implements UserDetailsService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!"askdog".equals(username)) {
            throw new UsernameNotFoundException(format("User [%s] not found.", username));
        }

        // TODO: 7/11/16 login for postman
        Buyer buyer = buyerRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(format("User [%s] not found.", username)));
        BuyerDetail buyerDetail = new BuyerDetail();
        buyerDetail.setId(buyer.getUuid());
        buyerDetail.setBuyerId(buyer.getBuyerId());
        buyerDetail.setName(buyer.getName());
        buyerDetail.setPassword("123456");
        return new AdBuyerDetails(buyerDetail);
    }
}