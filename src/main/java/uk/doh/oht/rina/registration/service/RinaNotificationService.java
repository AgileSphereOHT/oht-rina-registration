package uk.doh.oht.rina.registration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.rina.registration.config.RestProperties;
import uk.doh.oht.rina.registration.domain.notifications.Notification;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Slf4j
@Service
public class RinaNotificationService {
    private final ServiceHelper serviceHelper;
    private final RestTemplate restTemplate;
    private final RestProperties restProperties;

    @Inject
    public RinaNotificationService(final ServiceHelper serviceHelper,
                                   final RestTemplate restTemplate,
                                   final RestProperties restProperties) {
        this.serviceHelper = serviceHelper;
        this.restTemplate = restTemplate;
        this.restProperties = restProperties;
    }

    public List<Notification> retrieveNotificationsForDate(final String date) {
        final HttpHeaders headers = serviceHelper.createTokenHeader();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<List<Notification>> data = restTemplate.exchange(restProperties.buildNotificationPath() + "?date=" + date, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Notification>>() {});
        return data.getBody();
    }

    public List<Notification> retrieveNotificationsForCase(final String caseId) {
        final HttpHeaders headers = serviceHelper.createTokenHeader();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<List<Notification>> data = restTemplate.exchange(restProperties.buildNotificationPath() + "?caseId=" + caseId, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Notification>>() {});
        return data.getBody();
    }

    public Boolean updateNotification(final String notificationId) {
        return Boolean.TRUE;
    }
}
