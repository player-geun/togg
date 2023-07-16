package com.togg.banking.auth.presentation;

import com.togg.banking.auth.dto.SignUpRequest;
import com.togg.banking.auth.dto.SignUpResponse;
import com.togg.banking.common.ControllerTest;
import com.togg.banking.common.WithCustomMockUser;
import com.togg.banking.member.domain.InvestmentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerTest {

    @WithCustomMockUser
    @Test
    void 회원가입을_진행한다() throws Exception {
        // given
        SignUpResponse response = new SignUpResponse(1L, "g@gamil.com", "이근우", InvestmentType.SAFE);
        given(memberService.signUp(any(), any(SignUpRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(post("/sign-up")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(InvestmentType.SAFE)))
                )
                .andExpect(status().isCreated());
    }
}
