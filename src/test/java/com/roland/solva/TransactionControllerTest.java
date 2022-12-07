package com.roland.solva;

/**
 * @author Roland Pilpani 06.12.2022
 */

import com.roland.solva.controllers.TransactionController;
import com.roland.solva.enums.CurrencyType;
import com.roland.solva.enums.ExpenseCategory;
import com.roland.solva.models.Transaction;
import com.roland.solva.repositories.TransactionRepository;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
public class TransactionControllerTest {

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Sql(value = {"/sql/clearAll.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/setMonthLimit.sql")
    @Test
    public void registerTransactionTest() throws Exception {

        String json =
                "{\n" +
                "        \"account_from\": 123123,\n" +
                "        \"account_to\": 8899991555,\n" +
                "        \"currency_shortname\": \"RUB\",\n" +
                "        \"sum\": 23000.55,\n" +
                "        \"datetime\": \"2022-12-07 02:30:00+06\",\n" +
                "        \"expense_category\": \"SERVICE\"\n" +
                "}";



        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());


        Transaction transaction = transactionRepository.findById(1).get();

        assertThat(transaction.getCurrency()).isEqualTo(CurrencyType.RUB);
        assertThat(transaction.getSum()).isEqualTo(23000.55, Offset.offset(0.000001));
        assertThat(transaction.getExpenseCategory()).isEqualTo(ExpenseCategory.SERVICE);
        assertThat(transaction.getSumUsd()).isEqualTo(368.0088, Offset.offset(0.000001));
        assertThat(transaction.getAccountTo()).isEqualTo(8899991555L);
        assertThat(transaction.isLimitExceeded()).isFalse();

        String json2 =
                "{\n" +
                        "        \"account_from\": 123123,\n" +
                        "        \"account_to\": 8899881555,\n" +
                        "        \"currency_shortname\": \"KZT\",\n" +
                        "        \"sum\": 450000.55,\n" +
                        "        \"datetime\": \"2022-12-07 02:38:00+06\",\n" +
                        "        \"expense_category\": \"PRODUCT\"\n" +
                        "}";

        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        Transaction transaction2 = transactionRepository.findById(2).get();

        assertThat(transaction2.getCurrency()).isEqualTo(CurrencyType.KZT);
        assertThat(transaction2.getSum()).isEqualTo(450000.55, Offset.offset(0.00001));
        assertThat(transaction2.getExpenseCategory()).isEqualTo(ExpenseCategory.PRODUCT);
        assertThat(transaction2.getSumUsd()).isEqualTo(945.001155, Offset.offset(0.00001));
        assertThat(transaction2.getAccountTo()).isEqualTo(8899881555L);
        assertThat(transaction2.isLimitExceeded()).isTrue();
    }





    @Sql(value = {"/sql/clearAll.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/setMonthLimit.sql")
    @Test
    public void shouldReturnBadRequestDueToWrongAccountFrom() throws Exception {

        String json =
                "{\n" +
                        "        \"account_from\": -123123,\n" +
                        "        \"account_to\": 8899991555,\n" +
                        "        \"currency_shortname\": \"RUB\",\n" +
                        "        \"sum\": 23000.55,\n" +
                        "        \"datetime\": \"2022-12-07 02:30:00+06\",\n" +
                        "        \"expense_category\": \"SERVICE\"\n" +
                        "}";

        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }


    @Sql(value = {"/sql/clearAll.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/setMonthLimit.sql")
    @Test
    public void shouldReturnBadRequestDueToWrongAccountTo() throws Exception {

        String json =
                "{\n" +
                        "        \"account_from\": 123123,\n" +
                        "        \"account_to\": 8899991549995655,\n" +
                        "        \"currency_shortname\": \"RUB\",\n" +
                        "        \"sum\": 23000.55,\n" +
                        "        \"datetime\": \"2022-12-07 02:30:00+06\",\n" +
                        "        \"expense_category\": \"SERVICE\"\n" +
                        "}";

        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Sql(value = {"/sql/clearAll.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/setMonthLimit.sql")
    @Test
    public void shouldReturnBadRequestDueToWrongSum() throws Exception {

        String json =
                "{\n" +
                        "        \"account_from\": 123123,\n" +
                        "        \"account_to\": 89995655,\n" +
                        "        \"currency_shortname\": \"RUB\",\n" +
                        "        \"sum\": -23000.55,\n" +
                        "        \"datetime\": \"2022-12-07 02:30:00+06\",\n" +
                        "        \"expense_category\": \"SERVICE\"\n" +
                        "}";

        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }


    @Sql(value = {"/sql/clearAll.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/setMonthLimit.sql")
    @Test
    public void shouldReturnBadRequestDueToLackOfData() throws Exception {

        String json =
                "{\n" +
                        "        \"account_from\": 123123,\n" +
                        "        \"account_to\": 89995655,\n" +
                        "        \"sum\": 23000.55,\n" +
                        "        \"datetime\": \"2022-12-07 02:30:00+06\",\n" +
                        "}";

        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }



}
