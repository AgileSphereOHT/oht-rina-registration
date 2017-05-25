package uk.doh.oht.rina.registration.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.rina.domain.TimeSlot;
import uk.doh.oht.rina.domain.notifications.Notification;
import uk.doh.oht.rina.registration.config.RestProperties;

import javax.inject.Inject;
import java.util.Date;
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

    public List<Notification> retrieveNotificationsForDate(final Date date) {
        try {
            log.info("Enter retrieveNotificationsForDate");
            final HttpHeaders headers = serviceHelper.createTokenHeader();
            final HttpEntity<String> entity = new HttpEntity<>(null, headers);
            final ResponseEntity<List<Notification>> data = restTemplate.exchange(
                    restProperties.buildNotificationPath() + "?date=" + convertDateToString(date), HttpMethod.GET, entity, new ParameterizedTypeReference<List<Notification>>() {});
            return data.getBody();
        } finally {
            log.info("Exit retrieveNotificationsForDate");
        }
    }

    public List<Notification> retrieveNotificationsForCase(final String caseId, final Date date) {
        try {
            log.info("Enter retrieveNotificationsForCase");
            final HttpHeaders headers = serviceHelper.createTokenHeader();
            final HttpEntity<String> entity = new HttpEntity<>(null, headers);
            final ResponseEntity<List<Notification>> data = restTemplate.exchange(
                    restProperties.buildNotificationPath() + "?date=" + convertDateToString(date) + "&caseId=" + caseId, HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<Notification>>() {});
            return data.getBody();
        } finally {
            log.info("Exit retrieveNotificationsForCase");
        }
    }

    private String convertDateToString(final Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }

    public Boolean updateNotification(final String notificationId) {
        log.debug("updateNotification notificationId:{}", notificationId);
        return Boolean.TRUE;
    }

    public List<TimeSlot> retrieveTimeSlotsForCase(final String caseId) {
        try {
            log.info("Enter retrieveTimeSlotsForCase");
            final HttpHeaders headers = serviceHelper.createTokenHeader();
            final HttpEntity<String> entity = new HttpEntity<>(null, headers);
            final ResponseEntity<List<TimeSlot>> data = restTemplate.exchange(
                    restProperties.buildNotificationPath() + "TimeSlots?caseId=" + caseId, HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<TimeSlot>>() {});
            return data.getBody();
        } finally {
            log.info("Exit retrieveTimeSlotsForCase");
        }
    }
}
