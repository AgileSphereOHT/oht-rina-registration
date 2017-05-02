package uk.doh.oht.rina.registration.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.doh.oht.rina.registration.service.RinaExistingCaseService;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@RestController
public class ExistingCaseController {
    private final RinaExistingCaseService rinaExistingCaseService;

    @Inject
    public ExistingCaseController(final RinaExistingCaseService rinaExistingCaseService) {
        this.rinaExistingCaseService = rinaExistingCaseService;
    }

    @GetMapping(value = "/rina-registration/get-case/{caseId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Get a caseId",
            notes = "Send a request to return case details for caseId in RINA sub system"
    )
    public ResponseEntity<Map<String, Object>> getCase(
            @ApiParam(name = "caseId", value = "caseId of case to be returned from RINA sub system")
            @PathVariable final String caseId) {
        return ResponseEntity.ok().body(rinaExistingCaseService.getCase(caseId));
    }

    @GetMapping(value = "/rina-registration/get-document/{caseId}/{documentId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Get a case document",
            notes = "Send a request to return case document details for caseId/documentId in RINA sub system"
    )
    public ResponseEntity<Map<String, Object>> getDocument(
            @ApiParam(name = "caseId", value = "caseId of case to be returned from RINA sub system")
            @PathVariable final String caseId,
            @ApiParam(name = "documentId", value = "documentId of document in case to be returned from RINA sub system")
            @PathVariable final String documentId) {
        return ResponseEntity.ok().body(rinaExistingCaseService.getDocument(caseId, documentId));
    }

    @GetMapping(value = "/rina-registration/get-all-cases", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Get a list of all cases",
            notes = "Send a request to return case details for caseId in RINA sub system"
    )
    public ResponseEntity<List<Map<String, Object>>> getAllCases() {
        return ResponseEntity.ok().body(rinaExistingCaseService.getAllCases());
    }
}
