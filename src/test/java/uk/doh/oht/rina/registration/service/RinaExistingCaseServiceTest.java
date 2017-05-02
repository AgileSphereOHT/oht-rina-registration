package uk.doh.oht.rina.registration.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.rina.registration.config.RestProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RinaExistingCaseServiceTest {
    private RinaExistingCaseService rinaExistingCaseService;
    private final static String CASE_ID_VALUE = "1";
    private final static String DOCUMENT_ID_VALUE = "1";

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Mock
    private RestProperties restProperties;

    private final Map<String, Object> mapData = new HashMap<>();
    private final List<Map<String, Object>> listData = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        rinaExistingCaseService = new RinaExistingCaseService(oAuth2RestTemplate, restTemplate, restProperties);
        mapData.put(CASE_ID_VALUE, "{test-data}");
        listData.add(mapData);
    }

    @Test
    public void testGetAllCases() throws Exception {
        final OAuth2AccessToken accessToken = new DefaultOAuth2AccessToken("5608842a-f35d-4666-9daa-42027eb3b4de");
        final ResponseEntity<List<Map<String, Object>>> responseEntity = new ResponseEntity(listData, HttpStatus.OK);

        given(oAuth2RestTemplate.getAccessToken()).willReturn(accessToken);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<List<Map<String, Object>>>> any())).willReturn(responseEntity);
        assertThat(rinaExistingCaseService.getAllCases(), is(listData));
    }

    @Test
    public void testGetCase() throws Exception {
        final OAuth2AccessToken accessToken = new DefaultOAuth2AccessToken("5608842a-f35d-4666-9daa-42027eb3b4de");
        final ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity(mapData, HttpStatus.OK);

        given(oAuth2RestTemplate.getAccessToken()).willReturn(accessToken);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<Map<String, Object>>> any())).willReturn(responseEntity);
        assertThat(rinaExistingCaseService.getCase(CASE_ID_VALUE), is(mapData));
    }

    @Test
    public void testGetDocument() throws Exception {
        final OAuth2AccessToken accessToken = new DefaultOAuth2AccessToken("5608842a-f35d-4666-9daa-42027eb3b4de");
        final ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity(mapData, HttpStatus.OK);

        given(oAuth2RestTemplate.getAccessToken()).willReturn(accessToken);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<Map<String, Object>>> any())).willReturn(responseEntity);
        assertThat(rinaExistingCaseService.getDocument(CASE_ID_VALUE, DOCUMENT_ID_VALUE), is(mapData));
    }
}