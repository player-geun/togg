package com.togg.banking.auth.presentation;

import com.togg.banking.auth.application.JwtProvider;
import com.togg.banking.auth.application.JwtService;
import com.togg.banking.member.application.MemberService;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login";
    private static final String BEARER = "Bearer ";

    private final JwtProvider jwtProvider;
    private final JwtService jwtService;
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
                .orElseThrow();
        Member member = memberService.findByEmail(email);
        saveAuthentication(member);
    }

    private void saveAuthentication(Member member) {
        UserDetails userDetails = User.builder()
                .username(member.getEmail())
                .build();

        GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void authenticateWithRefreshToken(HttpServletResponse response, String refreshToken) {
        String updatedRefreshToken = jwtProvider.createRefreshToken();
        jwtService.updateByRefreshToken(refreshToken, updatedRefreshToken);
        response.setStatus(HttpServletResponse.SC_OK);

        Member member = memberService.findByRefreshToken(updatedRefreshToken);
        String accessToken = jwtProvider.createAccessToken(member.getEmail());
        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, updatedRefreshToken);
    }
}
