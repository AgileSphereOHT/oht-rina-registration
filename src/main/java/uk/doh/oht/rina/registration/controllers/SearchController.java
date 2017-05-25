package uk.doh.oht.rina.registration.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.doh.oht.rina.domain.OpenCaseSearchResult;
import uk.doh.oht.rina.registration.service.RinaSearchResultsService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by peterwhitehead on 04/05/2017.
 */
@Slf4j
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
    public ResponseEntity<List<OpenCaseSearchResult>> searchCases(@RequestParam final String searchText) {
        try {
            log.info("Enter searchCases");
            return ResponseEntity.ok().body(rinaSearchResultsService.searchCases(searchText));
        } finally {
            log.info("Exit searchCases");
        }
    }
}
