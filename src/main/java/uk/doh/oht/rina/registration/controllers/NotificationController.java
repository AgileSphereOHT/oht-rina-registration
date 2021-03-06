package uk.doh.oht.rina.registration.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.doh.oht.rina.domain.notifications.Notification;
import uk.doh.oht.rina.registration.service.RinaNotificationService;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Slf4j
@RestController
public class NotificationController {
    private final RinaNotificationService rinaNotificationService;

    @Inject
    public NotificationController(final RinaNotificationService rinaNotificationService) {
        this.rinaNotificationService = rinaNotificationService;
    }

    @GetMapping(value = "/rina-registration/notifications-by-date", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Gets a list of all notifications for date",
            notes = "Send a request to return notifications for date in RINA sub system"
    )
    public ResponseEntity<List<Notification>> retrieveNotificationsForDate(@RequestParam final Date date) {
        try {
            log.info("Enter retrieveNotificationsForDate");
            return ResponseEntity.ok().body(rinaNotificationService.retrieveNotificationsForDate(date));
        } finally {
            log.info("Exit retrieveNotificationsForDate");
        }
    }

    @GetMapping(value = "/rina-registration/notifications-by-case", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Gets a list of all notifications for caseId",
            notes = "Send a request to return notifications for caseId in RINA sub system"
    )
    public ResponseEntity<List<Notification>> retrieveNotificationsForCase(@RequestParam final String caseId, @RequestParam final Date date) {
        try {
            log.info("Enter retrieveNotificationsForCase");
            return ResponseEntity.ok().body(rinaNotificationService.retrieveNotificationsForCase(caseId, date));
        } finally {
            log.info("Exit retrieveNotificationsForCase");
        }
    }
}
