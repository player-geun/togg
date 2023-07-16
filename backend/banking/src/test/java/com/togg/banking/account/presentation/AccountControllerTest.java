package com.togg.banking.account.presentation;

import com.togg.banking.account.application.AccountService;
import com.togg.banking.account.dto.AccountResponse;
import com.togg.banking.common.WithCustomMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer aaaaaaaa.bbbbbbbb.cccccccc";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @WithCustomMockUser
    @Test
    void 계좌를_등록한다() throws Exception {
        // given
        AccountResponse response = new AccountResponse(1L, "100012345678", 1000);
        given(accountService.create(any())).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/accounts")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }
}
