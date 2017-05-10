package uk.doh.oht.rina.registration.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

/**
 * Created by peterwhitehead on 08/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceHelperTest {
    private ServiceHelper serviceHelper;

    @Mock
    private OAuth2RestTemplate eessiRestTemplate;
    @Mock
    private OAuth2AccessToken oAuth2AccessToken;

    private HttpHeaders httpHeaders;

    @Before
    public void setUp() throws Exception {
        serviceHelper = new ServiceHelper(eessiRestTemplate);
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer ebe16d8f-0c79-4766-8fba-50fd77c21533");
    }

    @Test
    public void testCreateTokenHeader() throws Exception {
        given(eessiRestTemplate.getAccessToken()).willReturn(oAuth2AccessToken);
        given(oAuth2AccessToken.getValue()).willReturn("ebe16d8f-0c79-4766-8fba-50fd77c21533");

        HttpHeaders returnedHttpHeaders = serviceHelper.createTokenHeader();
        assertThat(httpHeaders, is(returnedHttpHeaders));
    }
}