package com.gamesys.api.register.controller;

import com.gamesys.api.register.Application;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class RegisterControllerIT {

    private static final LocalDate TEN_YEARS_BACK = LocalDate.now().minusYears(10);

    private static final RegisterRequest REGISTER_REQUEST_1 = new RegisterRequest.Builder()
            .withUserName("testFrench1")
            .withPassword("Password1")
            .withDob("2000-01-01")
            .withPaymentCardNumber("349293081054422")
            .build();

    private static final RegisterRequest REGISTER_REQUEST_2 = new RegisterRequest.Builder()
            .withUserName("testFrench2")
            .withPassword("Password1")
            .withDob("1998-01-01")
            .withPaymentCardNumber("679299881054434")
            .build();

    private static final RegisterRequest REGISTER_REQUEST_3 = new RegisterRequest.Builder()
            .withUserName("testFrench3")
            .withPassword("Password1")
            .withDob("1988-01-01")
            .withPaymentCardNumber("909295381054564")
            .build();

    private static final RegisterRequest REGISTER_REQUEST_INVALID_CARD = new RegisterRequest.Builder()
            .withUserName("testFrench")
            .withPassword("Password1")
            .withDob("1997-01-01")
            .withPaymentCardNumber("34929")
            .build();

    private static final RegisterRequest REGISTER_REQUEST_UNDER_18 = new RegisterRequest.Builder()
            .withUserName("testFrench")
            .withPassword("Password1")
            .withDob(TEN_YEARS_BACK.toString())
            .withPaymentCardNumber("349293081054422")
            .build();

    private static final RegisterRequest REGISTER_REQUEST_INVALID_PASSWORD = new RegisterRequest.Builder()
            .withUserName("testFrench")
            .withPassword("password1")
            .withDob("1999-01-01")
            .withPaymentCardNumber("349293081054422")
            .build();

    private static final RegisterRequest REGISTER_REQUEST_BLOCKED_CARD = new RegisterRequest.Builder()
            .withUserName("testFrench")
            .withPassword("Password1")
            .withDob("1999-01-01")
            .withPaymentCardNumber("555555081054422")
            .build();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testCreateRegisterAndGetRegisterById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(REGISTER_REQUEST_1)))
                .andExpect(status().isCreated())
                .andReturn();

        RegisterResponse response = jsonToRegisterResponse(mvcResult.getResponse().getContentAsString());

        mockMvc.perform(get(String.format("/api/v1/registers/%s", response.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(REGISTER_REQUEST_1.getUserName()))
                .andExpect(jsonPath("$.password").value(REGISTER_REQUEST_1.getPassword()))
                .andExpect(jsonPath("$.dob").value(REGISTER_REQUEST_1.getDob()))
                .andExpect(jsonPath("$.paymentCardNumber").value(REGISTER_REQUEST_1.getPaymentCardNumber()));

    }

    @Test
    public void testCreateRegister_withDuplicateUserName() throws Exception {

        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(REGISTER_REQUEST_2)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(REGISTER_REQUEST_2)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("[USER_NAME_EXISTS]"));
    }

    @Test
    public void testCreateRegister_withDobUnder18() throws Exception {

        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(REGISTER_REQUEST_UNDER_18)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("[AGE_UNDER_18]"));
    }

    @Test
    public void testCreateRegister_withInvalidPassword() throws Exception {

        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(REGISTER_REQUEST_INVALID_PASSWORD)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("[PASSWORD_INVALID]"));
    }

    @Test
    public void testCreateRegister_withInvalidCardNumber() throws Exception {

        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(REGISTER_REQUEST_INVALID_CARD)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("[PAYMENT_CARD_NUMBER_INVALID]"));
    }

    @Test
    public void testCreateRegister_withBlockedCardNumber() throws Exception {

        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(REGISTER_REQUEST_BLOCKED_CARD)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("[PAYMENT_CARD_ISSUER_BLOCKED]"));
    }

    @Test
    public void testGetAllRegistered() throws Exception {
        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(REGISTER_REQUEST_3)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/registers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].userName").value(REGISTER_REQUEST_3.getUserName()))
                .andExpect(jsonPath("$[1].password").value(REGISTER_REQUEST_3.getPassword()))
                .andExpect(jsonPath("$[1].dob").value(REGISTER_REQUEST_3.getDob()))
                .andExpect(jsonPath("$[1].paymentCardNumber").value(REGISTER_REQUEST_3.getPaymentCardNumber()));
    }

    private static String asJsonString(final Object obj) {
        return new GsonBuilder().create().toJson(obj);
    }

    private static RegisterResponse jsonToRegisterResponse(final String json) {
        return new GsonBuilder().create().fromJson(json, RegisterResponse.class);
    }
}