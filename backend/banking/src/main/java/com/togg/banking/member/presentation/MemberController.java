package com.togg.banking.member.presentation;

import com.togg.banking.auth.dto.LoginMember;
import com.togg.banking.member.application.MemberService;
import com.togg.banking.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> findMe(@AuthenticationPrincipal LoginMember member) {
        MemberResponse response = memberService.findById(member.id());
        return ResponseEntity.ok(response);
    }
}
