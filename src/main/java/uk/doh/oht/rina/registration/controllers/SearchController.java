package uk.doh.oht.rina.registration.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.doh.oht.rina.registration.domain.SearchResults;
import uk.doh.oht.rina.registration.service.RinaSearchResultsService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by peterwhitehead on 04/05/2017.
 */
@RestController
public class SearchController {
    private final RinaSearchResultsService rinaSearchResultsService;

    @Inject
    public SearchController(final RinaSearchResultsService rinaSearchResultsService) {
        this.rinaSearchResultsService = rinaSearchResultsService;
    }

    @GetMapping(value = "/rina-registration/search-cases", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Gets a list of all cases matching search criteria",
            notes = "Send a request to return cases matching search criteria in RINA sub system"
    )
    public ResponseEntity<List<SearchResults>> searchCases(@RequestParam String searchText) {
        return ResponseEntity.ok().body(rinaSearchResultsService.searchCases(searchText));
    }
}
