package com.roland.solva;

import com.roland.solva.controllers.ClientController;
import com.roland.solva.controllers.TransactionController;
import com.roland.solva.models.MonthLimit;
import com.roland.solva.repositories.MonthLimitRepository;
import com.roland.solva.services.LimitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Roland Pilpani 06.12.2022
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClientController clientController;

    @Autowired
    private MonthLimitRepository monthLimitRepository;


    @Sql(value = {"/sql/clearAll.sql"})
    @Test
    public void setLimitTest() throws Exception {
        mockMvc.perform(post("/client/set_limit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"account_id\": 1000000123, \"limit_sum_usd\": 5000.0 }")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        List<MonthLimit> monthLimits = monthLimitRepository.findByAccount_Id(1000000123);
        assertThat(monthLimits).isNotNull();
        assertThat(monthLimits.size()).isEqualTo(1);
        assertThat(monthLimits.get(0).getLimitSum()).isEqualTo(5000.0);
    }

    @Test
    @Sql(value = {"/sql/clearAll.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/sql/addTransactions.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/sql/clearAll.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllExceededTest() throws Exception {

        String s = "[\n" +
                "    {\n" +
                "        \"account_from\": 123,\n" +
                "        \"account_to\": 8899991555,\n" +
                "        \"currency_shortname\": \"RUB\",\n" +
                "        \"sum\": 23000.55,\n" +
                "        \"datetime\": \"2022-12-07 02:30:00+06\",\n" +
                "        \"expense_category\": \"SERVICE\",\n" +
                "        \"limit_sum\": 600.0,\n" +
                "        \"limit_datetime\": \"2022-12-07 01:42:55+06\",\n" +
                "        \"limit_currency_shortname\": \"USD\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"account_from\": 123,\n" +
                "        \"account_to\": 8897991555,\n" +
                "        \"currency_shortname\": \"KZT\",\n" +
                "        \"sum\": 23000.55,\n" +
                "        \"datetime\": \"2022-12-07 02:33:00+06\",\n" +
                "        \"expense_category\": \"SERVICE\",\n" +
                "        \"limit_sum\": 600.0,\n" +
                "        \"limit_datetime\": \"2022-12-07 01:42:55+06\",\n" +
                "        \"limit_currency_shortname\": \"USD\"\n" +
                "    }\n" +
                "]";

        mockMvc.perform(get("/client/get_exceeded/0000000123")).andExpect(content().json(s));
    }



    @Sql(value = {"/sql/clearAll.sql"})
    @Test
    public void shouldReturnBadRequestDueToNegativeLimitSum() throws Exception {
        mockMvc.perform(post("/client/set_limit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"account_id\": 1000000123, \"limit_sum_usd\": -5000.0 }")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Sql(value = {"/sql/clearAll.sql"})
    @Test
    public void shouldReturnBadRequestDueToWrongAccountId() throws Exception {
        mockMvc.perform(post("/client/set_limit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"account_id\": 400561000000123, \"limit_sum_usd\": 5000.0 }")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

}
