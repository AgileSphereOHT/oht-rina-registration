package uk.doh.oht.rina.registration.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.doh.oht.rina.domain.OpenCaseSearchResult;
import uk.doh.oht.rina.domain.TimeSlot;
import uk.doh.oht.rina.domain.common.Creator;
import uk.doh.oht.rina.domain.common.Organisation;
import uk.doh.oht.rina.domain.notifications.Document;
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
public class RinaSearchResultServiceTest {
    private RinaSearchResultsService rinaSearchResultsService;

    @Mock
    private RinaExistingCaseService rinaExistingCaseService;
    @Mock
    private RinaNotificationService rinaNotificationService;

    private List<OpenCaseSearchResult> openCaseSearchResultList;
    private List<TimeSlot> timeSlotList;
    private List<Notification> notificationList;

    @Before
    public void setUp() throws Exception {
        rinaSearchResultsService = new RinaSearchResultsService(rinaExistingCaseService, rinaNotificationService);

        openCaseSearchResultList = new ArrayList<>();
        final OpenCaseSearchResult openCaseSearchResult = new OpenCaseSearchResult();
        openCaseSearchResultList.add(openCaseSearchResult);

        timeSlotList = new ArrayList<>();
        final TimeSlot timeSlot = new TimeSlot();
        timeSlotList.add(timeSlot);

        createNotification();
    }

    @Test
    public void testSearchCases() throws Exception {
        given(rinaExistingCaseService.searchCases(anyString())).willReturn(openCaseSearchResultList);
        given(rinaNotificationService.retrieveTimeSlotsForCase(anyString())).willReturn(timeSlotList);
        given(rinaNotificationService.retrieveNotificationsForCase(anyString(), Mockito.<Date> any())).willReturn(notificationList);
        final List<OpenCaseSearchResult> returnedOpenCaseSearchResultList = rinaSearchResultsService.searchCases("test");
        assertThat(openCaseSearchResultList, is(returnedOpenCaseSearchResultList));
    }

    private void createNotification() {
        notificationList = new ArrayList<>();
        final Notification notification = new Notification();
        final Document document = new Document();
        document.setType("S073");
        notification.setDocument(document);
        final Creator creator = new Creator();
        final Organisation organisation = new Organisation();
        organisation.setCountryCode("UK");
        creator.setOrganisation(organisation);
        notification.setCreator(creator);
        notificationList.add(notification);
    }
}