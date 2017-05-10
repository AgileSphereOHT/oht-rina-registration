package uk.doh.oht.rina.registration.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.doh.oht.rina.registration.domain.notifications.Notification;
import uk.doh.oht.rina.registration.service.RinaNotificationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 08/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NotificationControllerTest {
    private final static String REST_NOTIFICATION_BY_CASE_URI = "/rina-registration/notifications-by-case";
    private final static String REST_NOTIFICATION_BY_DATE_URI = "/rina-registration/notifications-by-date";
    private final static String CASE_ID_VALUE = "1";

    private MockMvc mockMvc;

    @Mock
    private RinaNotificationService rinaNotificationService;

    private List<Notification> notificationList;
    private Date today;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new NotificationController(rinaNotificationService))
                .build();
        notificationList = new ArrayList<>();
        final Notification notification = new Notification();
        notification.setCaseId(CASE_ID_VALUE);
        notificationList.add(notification);
        today = new Date();
    }

    @Test
    public void testRetrieveNotificationsForDate() throws Exception {
        given(rinaNotificationService.retrieveNotificationsForDate(Mockito.<Date> any())).willReturn(notificationList);
        mockMvc.perform(MockMvcRequestBuilders.get(REST_NOTIFICATION_BY_DATE_URI)
                .param("date", "03/05/2017"))
                .andExpect(handler().methodName("retrieveNotificationsForDate"))
                .andExpect(handler().handlerType(NotificationController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("[{\"severity\":null,\"reason\":null,\"creator\":null,\"dueDate\":null,\"document\":null,\"isRead\":null,\"slot\":null,\"assignmentRequest\":null,\"creationDate\":null,\"type\":null,\"caseData\":null,\"expanded\":null,\"responsibleParties\":null,\"sourceType\":null,\"lastUpdate\":null,\"caseId\":\"1\",\"isSelected\":null,\"failureReason\":null,\"id\":null,\"typeObj\":null,\"isFilteredOut\":null,\"category\":null,\"status\":null,\"severityObj\":null}]"));

    }

    @Test
    public void testRetrieveNotificationsForCase() throws Exception {
        given(rinaNotificationService.retrieveNotificationsForCase(anyString(), Mockito.<Date> any())).willReturn(notificationList);
        mockMvc.perform(MockMvcRequestBuilders.get(REST_NOTIFICATION_BY_CASE_URI)
                .param("caseId", CASE_ID_VALUE)
                .param("date", "03/05/2017"))
                .andExpect(handler().methodName("retrieveNotificationsForCase"))
                .andExpect(handler().handlerType(NotificationController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("[{\"severity\":null,\"reason\":null,\"creator\":null,\"dueDate\":null,\"document\":null,\"isRead\":null,\"slot\":null,\"assignmentRequest\":null,\"creationDate\":null,\"type\":null,\"caseData\":null,\"expanded\":null,\"responsibleParties\":null,\"sourceType\":null,\"lastUpdate\":null,\"caseId\":\"1\",\"isSelected\":null,\"failureReason\":null,\"id\":null,\"typeObj\":null,\"isFilteredOut\":null,\"category\":null,\"status\":null,\"severityObj\":null}]"));
    }
}