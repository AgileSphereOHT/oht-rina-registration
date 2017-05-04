package uk.doh.oht.rina.registration.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Component
public class ServiceHelper {
    private final OAuth2RestTemplate eessiRestTemplate;

    @Inject
    public ServiceHelper(final OAuth2RestTemplate eessiRestTemplate) {
        this.eessiRestTemplate = eessiRestTemplate;
    }

    public HttpHeaders createTokenHeader() {
        final OAuth2AccessToken oAuth2AccessToken  = eessiRestTemplate.getAccessToken();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + oAuth2AccessToken.getValue());
        return headers;
    }

}
