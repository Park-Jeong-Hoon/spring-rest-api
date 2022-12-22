package com.example.restapi.service;

import com.example.restapi.model.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    public void join() throws Exception {

        // given
        Member member = new Member();
        member.setUsername("test");
        member.setPassword("1234");
        member.setName("test");

        // when
        Long saveId = memberService.join(member);

        // then
        Assertions.assertEquals("test", memberService.getById(saveId).getUsername());
    }

    @Test
    public void validateDuplicate() throws Exception {

        // given
        Member member1 = new Member();
        member1.setUsername("test");
        member1.setPassword("1234");
        member1.setName("test");

        Member member2 = new Member();
        member2.setUsername("test");
        member2.setPassword("1234");
        member2.setName("test");

        // when
        memberService.join(member1);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        }, "중복 회원 예외가 발생해야 한다.");
    }
}
