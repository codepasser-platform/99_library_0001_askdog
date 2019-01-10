package com.askdog.store.model.security;

public interface Authority {

    String authority();

    enum Role implements Authority {

        BUYER, ADMIN, CONTENT_ADMIN;

        @Override
        public String authority() {
            return "ROLE_" + this.name();
        }
    }

}
