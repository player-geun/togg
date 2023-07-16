package com.togg.banking.account.presentation;

import com.togg.banking.account.dto.AccountResponse;
import com.togg.banking.common.ControllerTest;
import com.togg.banking.common.WithCustomMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends ControllerTest {

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
