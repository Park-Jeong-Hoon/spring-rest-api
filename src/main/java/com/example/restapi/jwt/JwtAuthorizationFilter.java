package com.example.restapi.jwt;

import com.example.restapi.auth.PrincipalDetails;
import com.example.restapi.model.Member;
import com.example.restapi.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인증이나 권한이 필요한 주소로 요청이 오면 BasicAuthenticationFilter 을 타게 되있음 (인증, 권한이 필요 없는 주소는 안탐)
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberRepository memberRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }

    // 인증이나 권한이 필요한 주소로 요청이 오면 이 필터를 탄다
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain);
        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader: " + jwtHeader);

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰 검증
        String jwtToken = jwtHeader.replace("Bearer ", "");

        Claims claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey("asdfKlja124o98qrchO8cawcl8fn48Ruqoh328hr29h3uwerhl21qil8Leuu93reWPalh23oI157p0w8439u3qfkeAjfoirowafiowQpurwknz").build()
                    .parseClaimsJws(jwtToken).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Long id = Long.parseLong(String.valueOf(claims.get("id")));

        Member member = memberRepository.findById(id).get();

        PrincipalDetails principalDetails = new PrincipalDetails(member);

        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
