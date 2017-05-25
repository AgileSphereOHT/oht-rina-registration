package uk.doh.oht.rina.registration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.rina.domain.OpenCaseSearchResult;
import uk.doh.oht.rina.domain.bucs.BucData;
import uk.doh.oht.rina.domain.documents.Document;
import uk.doh.oht.rina.domain.documents.S072;
import uk.doh.oht.rina.domain.documents.S073;
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
        try {
            log.info("Enter getCase");
            final HttpHeaders headers = serviceHelper.createTokenHeader();
            final HttpEntity<String> entity = new HttpEntity<>(null, headers);
            final ResponseEntity<BucData> data = restTemplate.exchange(
                    restProperties.buildCasePath() + caseId, HttpMethod.GET, entity, new ParameterizedTypeReference<BucData>() {});
            return data.getBody();
        } finally {
            log.info("Exit getCase");
        }
    }

    public Map<String, Object> getDocument(final String caseId, final String documentId) {
        try {
            log.info("Enter getDocument");
            final HttpHeaders headers = serviceHelper.createTokenHeader();
            final HttpEntity<String> entity = new HttpEntity<>(null, headers);
            final ResponseEntity<Map<String, Object>> data = restTemplate.exchange(
                    restProperties.buildCasePath() + caseId + restProperties.getRestRootDocumentPath() + documentId, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
            return data.getBody();
        } finally {
            log.info("Exit getDocument");
        }
    }

    public S073 getS073Document(final String caseId, final String documentId) {
        try {
            log.info("Enter getS073Document");
            final HttpHeaders headers = serviceHelper.createTokenHeader();
            final HttpEntity<String> entity = new HttpEntity<>(null, headers);
            final ResponseEntity<Document> data = restTemplate.exchange(
                    restProperties.buildCasePath() + caseId + restProperties.getRestRootDocumentPath() + documentId, HttpMethod.GET, entity, new ParameterizedTypeReference<Document>() {});
            return data.getBody().getS073();
        } finally {
            log.info("Exit getS073Document");
        }
    }

    public S072 getS072Document(final String caseId, final String documentId) {
        try {
            log.info("Enter getS072Document");
            final HttpHeaders headers = serviceHelper.createTokenHeader();
            final HttpEntity<String> entity = new HttpEntity<>(null, headers);
            final ResponseEntity<Document> data = restTemplate.exchange(
                    restProperties.buildCasePath() + caseId + restProperties.getRestRootDocumentPath() + documentId, HttpMethod.GET, entity, new ParameterizedTypeReference<Document>() {});
            return data.getBody().getS072();
        } finally {
            log.info("Exit getS072Document");
        }
    }

    public List<Map<String, Object>> getAllCases() {
        try {
            log.info("Enter getAllCases");
            final HttpHeaders headers = serviceHelper.createTokenHeader();
            final HttpEntity<String> entity = new HttpEntity<>(null, headers);
            final ResponseEntity<List<Map<String, Object>>> data = restTemplate.exchange(
                    restProperties.buildCasePath(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {});
            return data.getBody();
        } finally {
            log.info("Exit getAllCases");
        }
    }

    public List<OpenCaseSearchResult> searchCases(final String searchText) {
        try {
            log.info("Enter searchCases searchTest:{}", searchText);
            final HttpHeaders headers = serviceHelper.createTokenHeader();
            final HttpEntity<String> entity = new HttpEntity<>(null, headers);
            final ResponseEntity<List<OpenCaseSearchResult>> data = restTemplate.exchange(
                    restProperties.buildCasePath() + "?searchText=" + searchText, HttpMethod.GET, entity, new ParameterizedTypeReference<List<OpenCaseSearchResult>>() {});
            return data.getBody();
        } finally {
            log.info("Exit searchCases");
        }
    }

    public Boolean closeCase(final String caseId) {
        try {
            log.info("Enter closeCase caseId:{}", caseId);
            return Boolean.TRUE;
        } finally {
            log.info("Exit closeCase");
        }
    }
}
