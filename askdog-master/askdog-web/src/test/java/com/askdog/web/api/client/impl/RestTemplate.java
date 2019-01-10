package com.askdog.web.api.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Joiner.on;
import static org.springframework.http.HttpHeaders.*;

class RestTemplate extends org.springframework.web.client.RestTemplate {

    private CookieStore cookieStore;

    public RestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        setRequestFactory(requestFactory);
    }

    public RestTemplate(@Nonnull CookieStore cookieStore) {
        this();
        this.cookieStore = cookieStore;
    }

    public RestTemplate(ObjectMapper objectMapper, CookieStore cookieStore) {
        this(cookieStore);
        getMessageConverters().stream().filter(messageConverter -> messageConverter instanceof MappingJackson2HttpMessageConverter)
                .findFirst()
                .ifPresent(messageConverter -> ((MappingJackson2HttpMessageConverter) messageConverter).setObjectMapper(objectMapper));
    }

    public <T> ResponseEntity<T> postFormForEntity(URI url, MultiValueMap<String, String> parameters, Class<T> responseType) throws RestClientException {
        RequestCallback requestCallback = httpFormCallback(parameters);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
        return execute(url, HttpMethod.POST, requestCallback, responseExtractor);
    }

    public <T> ResponseEntity<T> putForEntity(URI url, Object request, Class<T> responseType) throws RestClientException {
        RequestCallback requestCallback = httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
        return execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
    }

    protected RequestCallback httpFormCallback(MultiValueMap<String, String> map) {
        return  request -> new FormHttpMessageConverter().write(map, MediaType.APPLICATION_FORM_URLENCODED, request);
    }

    @Override
    protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
        ClientHttpRequest request = super.createRequest(url, method);
        if (cookieStore != null) {
            request.getHeaders().add(COOKIE, on("; ").join(
                    cookieStore.get(request.getURI()).stream()
                            .map(httpCookie -> httpCookie.getName() + "=" + httpCookie.getValue())
                            .collect(Collectors.toList())
            ));
        }
        return request;
    }

    @Override
    protected void handleResponse(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        // do not handle the error response (include the client error and server error)
        if (cookieStore != null) {
            List<HttpCookie> httpCookies = new ArrayList<>();
            parseCookies(response, httpCookies, SET_COOKIE);
            parseCookies(response, httpCookies, SET_COOKIE2);
            httpCookies.forEach(httpCookie -> cookieStore.add(url, httpCookie));
        }
    }

    private void parseCookies(ClientHttpResponse response, List<HttpCookie> httpCookies, String cookieKey) {
        List<String> cookies = response.getHeaders().get(cookieKey);
        if (cookies != null) {
            cookies.forEach(cookie -> httpCookies.addAll(HttpCookie.parse(cookieKey + ":" + cookie)));
        }
    }

}
