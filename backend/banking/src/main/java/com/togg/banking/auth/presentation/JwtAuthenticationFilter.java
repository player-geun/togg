package com.togg.banking.auth.presentation;

import com.togg.banking.auth.application.JwtProvider;
import com.togg.banking.auth.domain.AuthenticationToken;
import com.togg.banking.auth.dto.LoginMember;
import com.togg.banking.member.application.MemberService;
import com.togg.banking.member.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login";
    private static final String BEARER = "Bearer ";

    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = extractToken(request, refreshHeader)
                .filter(jwtProvider::isValidToken)
                .orElse(null);
        if (refreshToken == null) {
            authenticateWithAccessToken(request);
            filterChain.doFilter(request, response);
            return;
        }

        authenticateWithRefreshToken(response, refreshToken);
        filterChain.doFilter(request, response);
    }

    private Optional<String> extractToken(HttpServletRequest request, String header) {
        return Optional.ofNullable(request.getHeader(header))
                .filter(token -> token.startsWith(BEARER))
                .map(token -> token.replace(BEARER, ""));
    }

    private void authenticateWithAccessToken(HttpServletRequest request) {
        String email = extractToken(request, accessHeader)
                .map(jwtProvider::extractEmail)
                .orElse(null);
        if (email == null) {
            return;
        }

        Member member = memberService.findByEmail(email);
        saveAuthentication(member);
    }

    private void saveAuthentication(Member member) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(member.getRole().name());
        AuthenticationToken authentication = new AuthenticationToken(
                authorities,
                new LoginMember(member.getId(), member.getName(), member.getEmail()),
                null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void authenticateWithRefreshToken(HttpServletResponse response, String refreshToken) {
        String updatedRefreshToken = jwtProvider.createRefreshToken();
        memberService.updateByRefreshToken(refreshToken, updatedRefreshToken);
        response.setStatus(HttpServletResponse.SC_OK);

        Member member = memberService.findByRefreshToken(updatedRefreshToken);
        String accessToken = jwtProvider.createAccessToken(member.getEmail());
        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, updatedRefreshToken);
    }
}
