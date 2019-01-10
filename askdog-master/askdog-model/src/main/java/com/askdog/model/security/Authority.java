package com.askdog.model.security;

public interface Authority {

    String authority();

    enum Role implements Authority {

        USER;

        @Override
        public String authority() {
            return "ROLE_" + USER.name();
        }
    }

}
