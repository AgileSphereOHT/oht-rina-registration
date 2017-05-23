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
import uk.doh.oht.rina.domain.OpenCaseSearchResult;
import uk.doh.oht.rina.registration.service.RinaSearchResultsService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 08/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SearchControllerTest {
    private final static String REST_SEARCH_URI = "/rina-registration/search-cases";

    private MockMvc mockMvc;

    @Mock
    private RinaSearchResultsService rinaSearchResultsService;

    private List<OpenCaseSearchResult> openCaseSearchResultList;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new SearchController(rinaSearchResultsService))
                .build();
        openCaseSearchResultList = new ArrayList<>();
        OpenCaseSearchResult openCaseSearchResult = new OpenCaseSearchResult();
        openCaseSearchResult.setCountryCode("UK");
        openCaseSearchResultList.add(openCaseSearchResult);
    }

    @Test
    public void testSearchCases() throws Exception {
        given(rinaSearchResultsService.searchCases(anyString())).willReturn(openCaseSearchResultList);
        mockMvc.perform(MockMvcRequestBuilders.get(REST_SEARCH_URI)
                .param("searchText", "open=cases"))
                .andExpect(handler().methodName("searchCases"))
                .andExpect(handler().handlerType(SearchController.class))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("[{\"serialVersionUID\":1,\"processDefinitionId\":null,\"traits\":null,\"id\":null,\"applicationRoleId\":null,\"properties\":null,\"status\":null,\"dueDate\":null,\"countryCode\":\"UK\",\"countryDescription\":null}]"));
    }
}