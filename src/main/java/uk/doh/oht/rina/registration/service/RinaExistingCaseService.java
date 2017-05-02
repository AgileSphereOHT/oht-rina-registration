package uk.doh.oht.rina.registration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.rina.registration.config.RestProperties;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Slf4j
@Service
public class RinaExistingCaseService {
    private final OAuth2RestTemplate eessiRestTemplate;
    private final RestTemplate restTemplate;
    private final RestProperties restProperties;

    @Inject
    public RinaExistingCaseService(final OAuth2RestTemplate eessiRestTemplate,
                                   final RestTemplate restTemplate,
                                   final RestProperties restProperties) {
        this.eessiRestTemplate = eessiRestTemplate;
        this.restTemplate = restTemplate;
        this.restProperties = restProperties;
    }

    public Map<String, Object> getCase(final String caseId) {
        final HttpHeaders headers = createTokenHeader();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<Map<String, Object>> data = restTemplate.exchange(restProperties.buildCasePath() + caseId, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
        return data.getBody();
    }

    public Map<String, Object> getDocument(final String caseId, final String documentId) {
        final HttpHeaders headers = createTokenHeader();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<Map<String, Object>> data = restTemplate.exchange(restProperties.buildCasePath() + caseId + restProperties.getRestRootDocumentPath() + documentId, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
        return data.getBody();
    }

    private HttpHeaders createTokenHeader() {
        final OAuth2AccessToken oAuth2AccessToken  = eessiRestTemplate.getAccessToken();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + oAuth2AccessToken.getValue());
        return headers;
    }

    public List<Map<String, Object>> getAllCases() {
        final HttpHeaders headers = createTokenHeader();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<List<Map<String, Object>>> data = restTemplate.exchange(restProperties.buildCasePath(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        return data.getBody();
    }
}
