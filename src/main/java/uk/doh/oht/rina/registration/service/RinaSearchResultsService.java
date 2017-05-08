package uk.doh.oht.rina.registration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.doh.oht.rina.registration.domain.OpenCaseSearchResult;
import uk.doh.oht.rina.registration.domain.TimeSlot;
import uk.doh.oht.rina.registration.domain.notifications.Notification;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Slf4j
@Service
public class RinaSearchResultsService {
    private static final String REGISTER_SED = "S073";
    private final RinaExistingCaseService rinaExistingCaseService;
    private final RinaNotificationService rinaNotificationService;

    @Inject
    public RinaSearchResultsService(final RinaExistingCaseService rinaExistingCaseService,
                                    final RinaNotificationService rinaNotificationService) {
        this.rinaExistingCaseService = rinaExistingCaseService;
        this.rinaNotificationService = rinaNotificationService;
    }

    public List<OpenCaseSearchResult> searchCases(final String searchText) {
        return filterReceivedS073Cases(rinaExistingCaseService.searchCases(searchText));
    }

    private List<OpenCaseSearchResult> filterReceivedS073Cases(List<OpenCaseSearchResult> originalResults) {
        final List<OpenCaseSearchResult> filteredOpenCaseSearchResults = new ArrayList<>();
        for (final OpenCaseSearchResult openCaseSearchResult : originalResults) {
            //process results open check if has S073 and is received
            //look for notifications to get due date
            filterWithTimeSlots(openCaseSearchResult, filteredOpenCaseSearchResults);
        }
        filteredOpenCaseSearchResults.sort(Comparator.comparing(OpenCaseSearchResult::getDueDate));
        return filteredOpenCaseSearchResults;
    }

    private Boolean filterWithNotifications(final Date date, final OpenCaseSearchResult openCaseSearchResult,
                                         final List<OpenCaseSearchResult> filteredOpenCaseSearchResults) {

        //convoluted way but you can't get notifications from the caseId itself you need a date
        //so get all timeslots for notifications on case to get dates loop through those to check for
        //S073
        final List<Notification> notifications = rinaNotificationService.retrieveNotificationsForCase(openCaseSearchResult.getId(), date);
        for (final Notification notification : notifications) {
            if (notification.getDocument().getType().equals(REGISTER_SED)) {
                openCaseSearchResult.setDueDate(notification.getDueDate());
                openCaseSearchResult.setCountryCode(notification.getCreator().getOrganisation().getCountryCode());
                filteredOpenCaseSearchResults.add(openCaseSearchResult);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private void filterWithTimeSlots(final OpenCaseSearchResult openCaseSearchResult,
                                     final List<OpenCaseSearchResult> filteredOpenCaseSearchResults) {
        final List<TimeSlot> timeSlots = rinaNotificationService.retrieveTimeSlotsForCase(openCaseSearchResult.getId());
        for (final TimeSlot timeSlot : timeSlots) {
            Boolean found = filterWithNotifications(timeSlot.getDate(), openCaseSearchResult, filteredOpenCaseSearchResults);
            if (found) {
                break;
            }
        }
    }
}
