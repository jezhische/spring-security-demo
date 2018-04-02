package com.jezh.springsecurity.controllerTest;

import com.jezh.springsecurity.baseTest.BaseIntegrationTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static java.util.jar.Attributes.Name.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllerTest extends BaseIntegrationTestConfig {

// http://www.baeldung.com/integration-testing-in-spring

// ------------------------------------------------------------------------------------------------- VERIFY VIEW NAME
// perform() method will call a get request method which returns the ResultActions. Using this result we can have
// assertion expectations on response like content, HTTP status, header, etc
//andDo(print()) will print the request and response. This is helpful to get detailed view in case of error
//andExpect() will expect the provided argument. In our case we are expecting “home” to be returned
// via MockMvcResultMatchers.view()
    @Test
    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
        mockMvc.perform(get("/")).andDo(print())
//                .andExpect(content().contentType(MediaType.ALL)) // AssertionError: Content type not set
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

// --------------------------------------------------------------------------------------------- VERIFY RESPONSE BODY

//    @Test
//    public void givenGreetURI_whenMockMVC_thenVerifyResponse() throws Exception {
//        final MvcResult mvcResult = mockMvc.perform(get("/greet"))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Hello World!!!"))
//                .andReturn();
//
//        Assert.assertEquals("application/json;charset=UTF-8",
//                mvcResult.getResponse().getContentType());
//
//        Assert.assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());
//    }

    // ---------------------------------------------------------------------------- SEND GET REQUEST with PATH VARIABLE
    @Test
    public void givenGreetURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
        mockMvc
                .perform(get("/common/testPathVar/{name}", "John"))
                .andDo(print()).andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.message").value("Hello World John!!!"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Hi, John"));
    }

    // ------------------------------------------------ VERIFY RESPONSE BODY - SEND GET REST REQUEST with PATH VARIABLE

//    @Test
//    public void restURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
//        mockMvc
//                .perform(get("/common/restTestPathVar/{name}", "John"))
//                .andDo(print()).andExpect(status().is(200))
////                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.restMsg").value("Hey, you, John!"));
//    }

    // --------------------------------------------------------------------------------------------- VERIFY RESPONSE BODY


}
