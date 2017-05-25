package uk.doh.oht.rina.registration.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uk.doh.oht.rina.domain.bucs.BucData;
import uk.doh.oht.rina.registration.domain.CustomerDetails;
import uk.doh.oht.rina.registration.service.RinaExistingCaseService;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Slf4j
@RestController
public class NewCaseController {
    private final RinaExistingCaseService rinaExistingCaseService;

    @Inject
    public NewCaseController(final RinaExistingCaseService rinaExistingCaseService) {
        this.rinaExistingCaseService = rinaExistingCaseService;
    }

    @PostMapping(value = "/rina-registration/create-buc01", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Create a BUC01",
            notes = "Send a request to register customer in RINA sub system"
    )
    public ResponseEntity<BucData> createbuc01(
            @ApiParam(name = "customerDetails", value = "Details of customer to register with RINA")
            @RequestBody
            @Valid final CustomerDetails customerDetails,
            final BindingResult bindingResult) {
        try {
            log.info("Enter createbuc01");
            return ResponseEntity.ok().body(rinaExistingCaseService.getCase("501"));
        } finally {
            log.info("Exit createbuc01");
        }
    }
}
