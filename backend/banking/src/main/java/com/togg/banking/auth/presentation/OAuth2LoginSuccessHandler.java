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

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

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
                                        Authentication authentication) {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        if (oAuth2User.isGuest()) {
            String accessToken = jwtProvider.createAccessToken(email);
            response.addHeader(accessHeader, BEARER + accessToken);

            setHeadersWithTokens(response, accessToken, null);
            return;
        }

        String accessToken = jwtProvider.createAccessToken(email);
        String refreshToken = jwtProvider.createRefreshToken();
        response.addHeader(accessHeader, BEARER + accessToken);
        response.addHeader(refreshHeader, BEARER + refreshToken);

        setHeadersWithTokens(response, accessToken, refreshToken);
        memberService.updateRefreshTokenByEmail(email, refreshToken);
    }

    private void setHeadersWithTokens(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);
    }
}
