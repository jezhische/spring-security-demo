package com.jezh.springsecurity.controllerTest;

import com.jezh.springsecurity.baseTest.BaseIntegrationTestConfig;
import com.jezh.springsecurity.user.CrmUser;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

// ------------------------------------------------------------------------------- VERIFY VIEW NAME AND MODEL ATTRIBUTE
//                                                                   RegistrationController.showMyLoginPage(Model model)

    @Test
    public void givenRegistrationFormPageURI_whenMockMVC_thenReturnRegistrationFormJSPViewName_andCrmUserModelAttribute()
            throws Exception {
        mockMvc.perform(get("/register/showRegistrationForm"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("WEB-INF/securityPgs/registration-form"))
                .andExpect(model().attributeExists("crmUser"))
// Если я пробую использовать .andExpect(model().attribute("crmUser", new CrmUser())), то получаю:
//              java.lang.AssertionError: Model attribute 'crmUser'
//              Expected :com.jezh.springsecurity.user.CrmUser@48bfb884
//              Actual   :com.jezh.springsecurity.user.CrmUser@349d0836
// Попробуем использовать public <T> ResultMatcher attribute(java.lang.String name, org.hamcrest.Matcher<T> matcher),
// при этом в качестве объекта интерфейса Matcher<T> возьмем класс IsInstanceOf, который имплементирует этот интерфейс,
// со статическим методом, который как раз и возвращает объект типа Matcher<T>:
                .andExpect(model().attribute("crmUser", org.hamcrest.core.IsInstanceOf.instanceOf(CrmUser.class)));
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
    public void givenURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
        mockMvc
                .perform(get("/common/testPathVar/{name}", "John"))
                .andDo(print()).andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.message").value("Hello World John!!!"))
                .andExpect(model().attribute("message", "Hi, John"));
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

    // ----------------------------------------------------------------------------------------------- ADMIN PAGES TEST
    //                                                  AdminManagingController.testPrincipal(ModelAndView modelAndView)
    @Test/*(expected = NullPointerException.class)*/
    public void givenURItestPrincipal_whenMockMVC_thenStatusOK_andModelAttribute() throws Exception {
//        String viewName =
        mockMvc
                .perform(get("/systems/testPrincipal"))
                .andDo(print())/*.andExpect(status().isOk())
                .andExpect(model().attributeExists("username"*//*, "password", "details", "authorities", "credentials"*//*))
                .andExpect(model().attribute("username", IsInstanceOf.instanceOf(String.class)))
                .andReturn().getModelAndView().getViewName()*/;
//        Assert.assertEquals("WEB-INF/testPgs/testPrincipal.jsp", viewName);
    }
}
