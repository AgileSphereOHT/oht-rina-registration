package uk.doh.oht.rina.registration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.rina.registration.config.RestProperties;
import uk.doh.oht.rina.registration.domain.bucs.BucData;
import uk.doh.oht.rina.registration.domain.OpenCaseSearchResult;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Slf4j
@Service
public class RinaExistingCaseService {
    private final ServiceHelper serviceHelper;
    private final RestTemplate restTemplate;
    private final RestProperties restProperties;

    @Inject
    public RinaExistingCaseService(final ServiceHelper serviceHelper,
                                   final RestTemplate restTemplate,
                                   final RestProperties restProperties) {
        this.serviceHelper = serviceHelper;
        this.restTemplate = restTemplate;
        this.restProperties = restProperties;
    }

    public BucData getCase(final String caseId) {
        final HttpHeaders headers = serviceHelper.createTokenHeader();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<BucData> data = restTemplate.exchange(restProperties.buildCasePath() + caseId, HttpMethod.GET, entity, new ParameterizedTypeReference<BucData>() {});
        return data.getBody();
    }

    public Map<String, Object> getDocument(final String caseId, final String documentId) {
        final HttpHeaders headers = serviceHelper.createTokenHeader();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<Map<String, Object>> data = restTemplate.exchange(restProperties.buildCasePath() + caseId + restProperties.getRestRootDocumentPath() + documentId, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
        return data.getBody();
    }

    public List<Map<String, Object>> getAllCases() {
        final HttpHeaders headers = serviceHelper.createTokenHeader();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<List<Map<String, Object>>> data = restTemplate.exchange(restProperties.buildCasePath(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        return data.getBody();
    }

    public List<OpenCaseSearchResult> searchCases(final String searchText) {
        final HttpHeaders headers = serviceHelper.createTokenHeader();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<List<OpenCaseSearchResult>> data = restTemplate.exchange(restProperties.buildCasePath() + "?searchText=" + searchText, HttpMethod.GET, entity, new ParameterizedTypeReference<List<OpenCaseSearchResult>>() {});
        return data.getBody();
    }

    public Boolean closeCase(final String caseId) {
        return Boolean.TRUE;
    }
}
