package com.togg.banking.auth.presentation;

import com.togg.banking.auth.application.JwtProvider;
import com.togg.banking.auth.domain.CustomOAuth2User;
import com.togg.banking.member.application.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String FRONT_URL = "http://localhost:3000";
    private static final String BEARER = "Bearer ";

    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Long id = oAuth2User.getId();
        if (oAuth2User.isGuest()) {
            String accessToken = jwtProvider.createAccessToken(String.valueOf(id));
            response.addHeader(accessHeader, BEARER + accessToken);

            setHeadersWithTokens(response, accessToken, null);
            response.sendRedirect(FRONT_URL + "/signup?token=" + BEARER + accessToken);
            return;
        }

        String accessToken = jwtProvider.createAccessToken(String.valueOf(id));
        String refreshToken = jwtProvider.createRefreshToken(null);
        response.addHeader(accessHeader, BEARER + accessToken);
        response.addHeader(refreshHeader, BEARER + refreshToken);

        setHeadersWithTokens(response, accessToken, refreshToken);
        memberService.updateRefreshTokenById(id, refreshToken);
        response.sendRedirect(FRONT_URL + "/sociallogin");
    }

    private void setHeadersWithTokens(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);
    }
}
