package com.askdog.store.web.configuration.handler;

public interface AjaxAuthenticationHandler<T extends AjaxAuthenticationHandler> {

    String DEFAULT_AJAX_PARAM = "ajax";

    T ajaxParam(String name);

}
