package com.togg.banking.auth.presentation;

import com.togg.banking.auth.dto.SignUpRequest;
import com.togg.banking.auth.dto.SignUpResponse;
import com.togg.banking.common.ControllerTest;
import com.togg.banking.common.WithCustomMockUser;
import com.togg.banking.common.fixtures.MemberFixtures;
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
        SignUpRequest request = MemberFixtures.SIGN_UP_REQUEST;
        SignUpResponse response = MemberFixtures.SIGN_UP_RESPONSE;
        given(memberService.signUp(any(), any(SignUpRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(post("/signup")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated());
    }
}
