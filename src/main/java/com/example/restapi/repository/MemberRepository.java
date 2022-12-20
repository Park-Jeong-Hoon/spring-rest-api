package com.example.restapi.repository;

import com.example.restapi.dto.MemberDto;
import com.example.restapi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);

    @Query("SELECT new com.example.restapi.dto.MemberDto(m.id, m.username, m.name) FROM Member m")
    List<MemberDto> getMemberDtoList();
}
