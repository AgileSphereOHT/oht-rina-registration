package uk.doh.oht.rina.registration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.doh.oht.rina.registration.domain.SearchResult;
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

    public List<SearchResult> searchCases(final String searchText) {
        return filterReceivedS073Cases(rinaExistingCaseService.searchCases(searchText));
    }

    private List<SearchResult> filterReceivedS073Cases(List<SearchResult> originalResults) {
        final List<SearchResult> filteredSearchResults = new ArrayList<>();
        for (final SearchResult searchResult : originalResults) {
            //process results open check if has S073 and is received
            //look for notifications to get due date
            filterWithTimeSlots(searchResult, filteredSearchResults);
        }
        filteredSearchResults.sort(Comparator.comparing(SearchResult::getDueDate));
        return filteredSearchResults;
    }

    private Boolean filterWithNotifications(final Date date, final SearchResult searchResult,
                                         final List<SearchResult> filteredSearchResults) {

        //convoluted way but you can't get notifications from the caseId itself you need a date
        //so get all timeslots for notifications on case to get dates loop through those to check for
        //S073
        final List<Notification> notifications = rinaNotificationService.retrieveNotificationsForCase(searchResult.getId(), date);
        for (final Notification notification : notifications) {
            if (notification.getDocument().getType().equals(REGISTER_SED)) {
                searchResult.setDueDate(notification.getDueDate());
                searchResult.setCountryCode(notification.getCreator().getOrganisation().getCountryCode());
                filteredSearchResults.add(searchResult);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private void filterWithTimeSlots(final SearchResult searchResult,
                                     final List<SearchResult> filteredSearchResults) {
        final List<TimeSlot> timeSlots = rinaNotificationService.retrieveTimeSlotsForCase(searchResult.getId());
        for (final TimeSlot timeSlot : timeSlots) {
            Boolean found = filterWithNotifications(timeSlot.getDate(), searchResult, filteredSearchResults);
            if (found) {
                break;
            }
        }
    }
}
