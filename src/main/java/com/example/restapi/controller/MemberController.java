package com.example.restapi.controller;

import com.example.restapi.auth.PrincipalDetails;
import com.example.restapi.dto.MemberDto;
import com.example.restapi.model.Member;
import com.example.restapi.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody Member member) {

        String result = "success";

        try {
            memberService.join(member);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<MemberDto> getById(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        Long id = principalDetails.getMember().getId();
        MemberDto memberDto = memberService.getById(id);

        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }
}
