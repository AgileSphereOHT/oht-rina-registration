package uk.doh.oht.rina.registration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.doh.oht.rina.registration.domain.SearchResults;
import uk.doh.oht.rina.registration.domain.notifications.Notification;

import javax.inject.Inject;
import java.util.ArrayList;
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

    public List<SearchResults> searchCases(final String searchText) {
        return filterReceivedS073Cases(rinaExistingCaseService.searchCases(searchText));
    }

    private List<SearchResults> filterReceivedS073Cases(List<SearchResults> originalResults) {
        final List<SearchResults> filteredSearchResults = new ArrayList<>();
        for (final SearchResults searchResults : originalResults) {
            //process results open check if has S073 and is received
            //look for notifications to get due date
            filterWithNotifications(searchResults, filteredSearchResults);
        }
        return filteredSearchResults;
    }

    private void filterWithNotifications(final SearchResults searchResults,
                                         final List<SearchResults> filteredSearchResults) {
        final List<Notification> notifications = rinaNotificationService.retrieveNotificationsForCase(searchResults.getId());
        for (final Notification notification : notifications) {
            if (notification.getDocument().getType().equals(REGISTER_SED)) {
                searchResults.setDueDate(notification.getDueDate());
                searchResults.setCountryCode(notification.getCreator().getOrganisation().getCountryCode());
                filteredSearchResults.add(searchResults);
                break;
            }
        }
    }
}
