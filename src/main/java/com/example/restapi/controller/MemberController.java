package com.example.restapi.controller;

import com.example.restapi.dto.MemberDto;
import com.example.restapi.model.Member;
import com.example.restapi.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {

        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getById(@PathVariable Long id) {

        MemberDto memberDto = memberService.getById(id);

        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberDto>> getMemberDtoList() {

        List<MemberDto> memberDtoList = memberService.getMemberDtoList();

        return new ResponseEntity<>(memberDtoList, HttpStatus.OK);
    }
}
