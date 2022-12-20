package com.example.restapi.service;

import com.example.restapi.dto.MemberDto;
import com.example.restapi.model.Member;
import com.example.restapi.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public MemberService(BCryptPasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public String join(Member member) throws Exception { // 회원가입

        String result = "success";

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");

        try {
            memberRepository.save(member);
        } catch (Exception e) {
            throw new Exception("save error");
        }

        return result;
    }

    public MemberDto getById(Long id) {

        Optional<Member> memberOptional = memberRepository.findById(id);

        if (memberOptional.isEmpty()) {
            return null;
        }

        Member member = memberOptional.get();
        MemberDto memberDto = new MemberDto(member.getId(), member.getUsername(), member.getName());

        return memberDto;
    }

    public List<MemberDto> getMemberDtoList() {

        List<MemberDto> memberList = memberRepository.getMemberDtoList();

        return memberList;
    }
}
