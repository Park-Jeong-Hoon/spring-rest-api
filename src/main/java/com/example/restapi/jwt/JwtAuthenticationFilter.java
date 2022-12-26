package com.example.restapi.jwt;

import com.example.restapi.auth.PrincipalDetails;
import com.example.restapi.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

// 원래는 /login 으로 username, password 를 가지고 post 요청이 들어오면 UsernamePasswordAuthenticationFilter 동작
// 하지만 SecurityConfig 에서 formLogin 을 disable 설정해놔서 동작안하기에 이 필터를 상속받아 JwtAuthenticationFilter 구현
// 이를 SecurityConfig 에 추가
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/member/login");
    }

    // /login 으로 요청이 들어왔을 때 로그인을 위해 실행되는 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Member member = objectMapper.readValue(request.getInputStream(), Member.class); // 클라이언트에서 보낸 username, password 받아옴

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken); // PrincipalDetailsService 의 loadUserByUsername 이 실행되고 정상이면 authentication 받음

            return authentication; // authentication 이 session 영역에 저장됨
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // attemptAuthentication 이 성공하면 실행되는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        SecretKey jwtKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JwtProperties.SECRET));

        String jwtToken = Jwts.builder()
                .setSubject("jwtToken")
                .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .setClaims(Map.of("id", principalDetails.getMember().getId()))
                .signWith(jwtKey, SignatureAlgorithm.HS512)
                .compact();

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.AUTH_TYPE + jwtToken);
    }
}
