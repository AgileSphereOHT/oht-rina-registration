package uk.doh.oht.rina.registration.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.doh.oht.rina.domain.bucs.BucData;
import uk.doh.oht.rina.domain.documents.S073;
import uk.doh.oht.rina.registration.service.RinaExistingCaseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExistingCaseControllerTest {
    private final static String REST_GET_CASE_URI = "/rina-registration/get-case/";
    private final static String REST_GET_DOCUMENT_URI = "/rina-registration/get-document/";
    private final static String REST_GET_S073_DOCUMENT_URI = "/rina-registration/get-s073-document/";
    private final static String REST_GET_ALL_CASES_URI = "/rina-registration/get-all-cases";
    private final static String CASE_ID_VALUE = "1";
    private final static String DOCUMENT_ID_VALUE = "1";

    private MockMvc mockMvc;

    @Mock
    private RinaExistingCaseService rinaExistingCaseService;

    private BucData bucData = new BucData();
    private S073 s073 = new S073();
    private final Map<String, Object> mapData = new HashMap<>();
    private final List<Map<String, Object>> listData = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ExistingCaseController(rinaExistingCaseService))
                .build();
        mapData.put(CASE_ID_VALUE, "{test-data}");
        listData.add(mapData);
        bucData.setProcessDefinitionName("test");
        s073.setSedGVer(4);
        s073.setSedPackage("Sector");
    }

    @Test
    public void testGetDocument() throws Exception {
        given(rinaExistingCaseService.getDocument(anyString(), anyString())).willReturn(mapData);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_GET_DOCUMENT_URI + CASE_ID_VALUE + "/" + DOCUMENT_ID_VALUE))
                .andExpect(handler().methodName("getDocument"))
                .andExpect(handler().handlerType(ExistingCaseController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"1\":\"{test-data}\"}"));
    }

    @Test
    public void testGetCase() throws Exception {
        given(rinaExistingCaseService.getCase(anyString())).willReturn(bucData);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_GET_CASE_URI + CASE_ID_VALUE))
                .andExpect(handler().methodName("getCase"))
                .andExpect(handler().handlerType(ExistingCaseController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"serialVersionUID\":1,\"creator\":null,\"documents\":null,\"subject\":null,\"processDefinitionName\":\"test\",\"sensitive\":null,\"lastUpdate\":null,\"id\":null,\"applicationRoleId\":null,\"actions\":null,\"startDate\":null,\"processDefinitionVersion\":null,\"properties\":null,\"participants\":null,\"status\":null}"));
    }

    @Test
    public void testGetAllCases() throws Exception {
        given(rinaExistingCaseService.getAllCases()).willReturn(listData);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_GET_ALL_CASES_URI))
                .andExpect(handler().methodName("getAllCases"))
                .andExpect(handler().handlerType(ExistingCaseController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("[{\"1\":\"{test-data}\"}]"));
    }

    @Test
    public void testGetS073Document() throws Exception {
        given(rinaExistingCaseService.getS073Document(anyString(), anyString())).willReturn(s073);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_GET_S073_DOCUMENT_URI + CASE_ID_VALUE + "/" + DOCUMENT_ID_VALUE))
                .andExpect(handler().methodName("getS073Document"))
                .andExpect(handler().handlerType(ExistingCaseController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"serialVersionUID\":1,\"sedPackage\":\"Sector\",\"sedGVer\":4,\"sedVer\":null,\"personFamilyName\":null,\"personForename\":null,\"personDateBirth\":null,\"personSex\":null,\"personFamilyNameAtBirth\":null,\"personForenameAtBirth\":null,\"Status\":null,\"Address\":null,\"InformationRegistration\":null,\"ConcernsDocument\":null,\"PeriodEntitlement\":null,\"Person\":null,\"IfPINNotProvidedForAnyInstitutionPleaseProvide\":null,\"AdditionalInformationPerson\":null,\"AddressOfPersonForWhomAnEntitlementDocumentRequete\":null,\"InsuredPerson\":null,\"AddressMainInsuredPerson\":null,\"StartingEndingDateRegistration\":null,\"ReasonForRefusalRegistration\":null,\"PINPersonInEachInstitution\":null}"));
    }
}