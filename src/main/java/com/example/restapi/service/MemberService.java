package com.example.restapi.service;

import com.example.restapi.model.Member;
import com.example.restapi.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public MemberService(BCryptPasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public String join(Member member) throws Exception {
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
}
