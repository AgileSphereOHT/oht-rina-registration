package uk.doh.oht.rina.registration.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.doh.oht.rina.domain.bucs.BucData;
import uk.doh.oht.rina.domain.documents.S072;
import uk.doh.oht.rina.domain.documents.S073;
import uk.doh.oht.rina.registration.service.RinaExistingCaseService;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Slf4j
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
    public ResponseEntity<BucData> getCase(
            @ApiParam(name = "caseId", value = "caseId of case to be returned from RINA sub system")
            @PathVariable final String caseId) {
        try {
            log.info("Enter getCase");
            return ResponseEntity.ok().body(rinaExistingCaseService.getCase(caseId));
        } finally {
            log.info("Exit getCase");
        }
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
        try {
            log.info("Enter getDocument");
            return ResponseEntity.ok().body(rinaExistingCaseService.getDocument(caseId, documentId));
        } finally {
            log.info("Exit getDocument");
        }
    }

    @GetMapping(value = "/rina-registration/get-all-cases", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Get a list of all cases",
            notes = "Send a request to return case details for caseId in RINA sub system"
    )
    public ResponseEntity<List<Map<String, Object>>> getAllCases() {
        try {
            log.info("Enter getAllCases");
            return ResponseEntity.ok().body(rinaExistingCaseService.getAllCases());
        } finally {
            log.info("Exit getAllCases");
        }
    }

    @GetMapping(value = "/rina-registration/get-s073-document/{caseId}/{documentId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Get a case document",
            notes = "Send a request to return case document details for caseId/documentId in RINA sub system"
    )
    public ResponseEntity<S073> getS073Document(
            @ApiParam(name = "caseId", value = "caseId of case to be returned from RINA sub system")
            @PathVariable final String caseId,
            @ApiParam(name = "documentId", value = "documentId of document in case to be returned from RINA sub system")
            @PathVariable final String documentId) {
        try {
            log.info("Enter getS073Document");
            return ResponseEntity.ok().body(rinaExistingCaseService.getS073Document(caseId, documentId));
        } finally {
            log.info("Exit getS073Document");
        }
    }

    @GetMapping(value = "/rina-registration/get-s072-document/{caseId}/{documentId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Get a case document",
            notes = "Send a request to return case document details for caseId/documentId in RINA sub system"
    )
    public ResponseEntity<S072> getS072Document(
            @ApiParam(name = "caseId", value = "caseId of case to be returned from RINA sub system")
            @PathVariable final String caseId,
            @ApiParam(name = "documentId", value = "documentId of document in case to be returned from RINA sub system")
            @PathVariable final String documentId) {
        try {
            log.info("Enter getS072Document");
            return ResponseEntity.ok().body(rinaExistingCaseService.getS072Document(caseId, documentId));
        } finally {
            log.info("Exit getS072Document");
        }
    }
}
