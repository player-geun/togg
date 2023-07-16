package com.togg.banking.auth.presentation;

import com.togg.banking.auth.application.JwtProvider;
import com.togg.banking.auth.dto.LoginMember;
import com.togg.banking.auth.dto.SignUpRequest;
import com.togg.banking.auth.dto.SignUpResponse;
import com.togg.banking.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request,
                                                 @AuthenticationPrincipal LoginMember loginMember) {
        SignUpResponse response = memberService.signUp(loginMember.id(), request);
        HttpHeaders headers = getHeadersWithTokens(loginMember.email());
        return ResponseEntity.created(URI.create("/api/members/me")).headers(headers).body(response);
    }

    private HttpHeaders getHeadersWithTokens(String email) {
        String accessToken = jwtProvider.createAccessToken(email);
        String refreshToken = jwtProvider.createRefreshToken();
        memberService.updateRefreshTokenByEmail(email, refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set(accessHeader, accessToken);
        headers.set(refreshHeader, refreshToken);
        return headers;
    }
}
