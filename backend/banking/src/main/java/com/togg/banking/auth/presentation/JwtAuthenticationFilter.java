package com.togg.banking.auth.presentation;

import com.togg.banking.auth.application.JwtProvider;
import com.togg.banking.auth.domain.AuthenticationToken;
import com.togg.banking.auth.dto.LoginMember;
import com.togg.banking.member.application.MemberService;
import com.togg.banking.member.dto.MemberResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

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
        try {
            String refreshToken = extractToken(request, refreshHeader).orElse(null);
            if (refreshToken == null) {
                authenticateWithAccessToken(request);
            } else {
                jwtProvider.validateToken(refreshToken);
                authenticateWithRefreshToken(response, refreshToken);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractToken(HttpServletRequest request, String header) {
        return Optional.ofNullable(request.getHeader(header))
                .filter(token -> token.startsWith(BEARER))
                .map(token -> token.replace(BEARER, ""));
    }

    private void authenticateWithAccessToken(HttpServletRequest request) {
        String memberId = extractToken(request, accessHeader)
                .map(jwtProvider::getSubject)
                .orElseThrow();

        MemberResponse response = memberService.findById(Long.valueOf(memberId));
        saveAuthentication(response);
    }

    private void saveAuthentication(MemberResponse response) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(response.role().name());
        AuthenticationToken authentication = new AuthenticationToken(
                authorities,
                new LoginMember(response.id(), response.name()),
                null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void authenticateWithRefreshToken(HttpServletResponse response, String refreshToken) {
        String updatedRefreshToken = jwtProvider.createRefreshToken(null);
        memberService.updateByRefreshToken(refreshToken, updatedRefreshToken);

        MemberResponse member = memberService.findByRefreshToken(updatedRefreshToken);
        String accessToken = jwtProvider.createAccessToken(String.valueOf(member.id()));

        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, updatedRefreshToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
