package uk.doh.oht.rina.registration.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.doh.oht.rina.registration.service.RinaExistingCaseService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NewCaseControllerTest {
    private final static String REST_CREATE_CASE_URI = "/rina-registration/create-buc01";
    private final static String CASE_ID_VALUE = "1";

    private MockMvc mockMvc;

    @Mock
    private RinaExistingCaseService rinaExistingCaseService;

    Map<String, Object> mapData = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new NewCaseController(rinaExistingCaseService))
                .build();
        mapData.put(CASE_ID_VALUE, "{test-data}");
    }

    @Test
    public void testCreateBuc01() throws Exception {
        given(rinaExistingCaseService.getCase(anyString())).willReturn(mapData);

        mockMvc.perform(MockMvcRequestBuilders.post(REST_CREATE_CASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(handler().methodName("createbuc01"))
                .andExpect(handler().handlerType(NewCaseController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(mapData.get(CASE_ID_VALUE).toString()));
    }
}