package com.togg.banking.member.presentation;

import com.togg.banking.common.ControllerTest;
import com.togg.banking.common.WithCustomMockUser;
import com.togg.banking.common.fixtures.MemberFixtures;
import com.togg.banking.member.dto.MemberResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTest {

    @WithCustomMockUser
    @Test
    void 나를_조회한다() throws Exception {
        // given
        MemberResponse response = MemberFixtures.MEMBER_RESPONSE;
        given(memberService.findById(any())).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/members/me")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
