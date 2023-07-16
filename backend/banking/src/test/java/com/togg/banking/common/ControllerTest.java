package com.togg.banking.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.togg.banking.account.application.AccountService;
import com.togg.banking.account.presentation.AccountController;
import com.togg.banking.auth.application.JwtProvider;
import com.togg.banking.auth.presentation.AuthController;
import com.togg.banking.member.application.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest({
        AccountController.class,
        AuthController.class
})
public abstract class ControllerTest {

    protected static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    protected static final String AUTHORIZATION_HEADER_VALUE = "Bearer aaaaaaaa.bbbbbbbb.cccccccc";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AccountService accountService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected JwtProvider jwtProvider;
}
