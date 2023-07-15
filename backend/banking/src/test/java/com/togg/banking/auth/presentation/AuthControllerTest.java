package com.togg.banking.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.togg.banking.auth.application.JwtProvider;
import com.togg.banking.auth.dto.SignUpRequest;
import com.togg.banking.auth.dto.SignUpResponse;
import com.togg.banking.common.WithCustomMockUser;
import com.togg.banking.member.application.MemberService;
import com.togg.banking.member.domain.InvestmentType;
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
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer aaaaaaaa.bbbbbbbb.cccccccc";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtProvider jwtProvider;

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
