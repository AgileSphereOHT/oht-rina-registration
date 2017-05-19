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
import org.springframework.web.client.RestTemplate;
import uk.doh.oht.rina.registration.config.RestProperties;
import uk.doh.oht.rina.domain.TimeSlot;
import uk.doh.oht.rina.domain.notifications.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Created by peterwhitehead on 08/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RinaNotificationServiceTest {
    private RinaNotificationService rinaNotificationService;
    private final static String CASE_ID_VALUE = "1";
    private final static String NOTIFICATION_ID_VALUE = "1";

    @Mock
    private ServiceHelper serviceHelper;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private RestProperties restProperties;

    private Date today;

    private List<Notification> notificationList;
    private List<TimeSlot> timeSlotList;

    @Before
    public void setUp() throws Exception {
        rinaNotificationService = new RinaNotificationService(serviceHelper, restTemplate, restProperties);
        today = new Date();
        notificationList = new ArrayList<>();
        final Notification notification = new Notification();
        notificationList.add(notification);
        timeSlotList = new ArrayList<>();
        final TimeSlot timeSlot = new TimeSlot();
        timeSlotList.add(timeSlot);
    }

    @Test
    public void testRetrieveNotificationsForDate() throws Exception {
        final ResponseEntity<List<Notification>> responseEntity = new ResponseEntity(notificationList, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<List<Notification>>> any())).willReturn(responseEntity);

        List<Notification> returnedNotificationList = rinaNotificationService.retrieveNotificationsForDate(today);
        assertThat(returnedNotificationList, is(returnedNotificationList));
    }

    @Test
    public void testRetrieveNotificationsForCase() throws Exception {
        final ResponseEntity<List<Notification>> responseEntity = new ResponseEntity(notificationList, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<List<Notification>>> any())).willReturn(responseEntity);

        List<Notification> returnedNotificationList = rinaNotificationService.retrieveNotificationsForCase(CASE_ID_VALUE, today);
        assertThat(returnedNotificationList, is(returnedNotificationList));
    }

    @Test
    public void testUpdateNotification() throws Exception {
        assertThat(rinaNotificationService.updateNotification(NOTIFICATION_ID_VALUE), is(Boolean.TRUE));
    }

    @Test
    public void testRetrieveTimeSlotsForCase() throws Exception {
        final ResponseEntity<List<TimeSlot>> responseEntity = new ResponseEntity(timeSlotList, HttpStatus.OK);
        given(restTemplate.exchange(anyString(), Mockito.<HttpMethod> any(), Mockito.<HttpEntity<String>> any(), Matchers.<ParameterizedTypeReference<List<TimeSlot>>> any())).willReturn(responseEntity);

        List<TimeSlot> returnedTimeSlotList = rinaNotificationService.retrieveTimeSlotsForCase(CASE_ID_VALUE);
        assertThat(returnedTimeSlotList, is(timeSlotList));
    }
}