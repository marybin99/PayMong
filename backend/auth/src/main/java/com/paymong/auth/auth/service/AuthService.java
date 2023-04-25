package com.paymong.auth.auth.service;

import com.paymong.auth.auth.dto.request.LoginReqDto;
import com.paymong.auth.auth.dto.request.RegisterReqDto;
import com.paymong.auth.auth.dto.response.LoginResDto;
import com.paymong.auth.auth.entity.Auth;
import com.paymong.auth.auth.entity.Member;
import com.paymong.auth.auth.repository.AuthRepository;
import com.paymong.auth.auth.repository.MemberRepository;
import com.paymong.auth.global.code.JwtStateCode;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.exception.UnAuthException;
import com.paymong.auth.global.redis.RefreshToken;
import com.paymong.auth.global.redis.RefreshTokenRedisRepository;
import com.paymong.auth.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final AuthRepository authRepository;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Transactional
    public LoginResDto login(LoginReqDto loginRequestDto)
        throws RuntimeException {

        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
            .orElseThrow(() -> new NotFoundException());

        if (!member.getPassword().equals(loginRequestDto.getPassword())) {
            throw new IllegalArgumentException();
        }

        // 토큰 발급

        String accessToken = tokenProvider.generateAccessToken(loginRequestDto.getEmail());

        String refreshToken = tokenProvider.generateRefreshToken(loginRequestDto.getEmail());

        // 역할 설정
        Auth auth = authRepository.findByMember(member).orElseThrow(() -> new NotFoundException());

        refreshTokenRedisRepository.save(
            RefreshToken.builder()
                .id(member.getEmail())
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .expiration(JwtStateCode.ACCESS_TOKEN_EXPIRATION_PERIOD.getValue())
                .build()
        );

        return LoginResDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .role(auth.getRole())
            .build();
    }

    @Transactional
    public void register(RegisterReqDto registerRequestDto) {
        Member member = registerRequestDto.toMember();
        memberRepository.save(member);
        Auth auth = Auth.of("USER", member);
        authRepository.save(auth);
    }

    @Transactional
    public LoginResDto reissue(String refreshToken) throws RuntimeException {
        refreshToken = refreshToken.substring(7);

        String email = tokenProvider.getUsername(refreshToken);

        RefreshToken redisRefreshToken = refreshTokenRedisRepository.findById(email)
            .orElseThrow(() -> new UnAuthException());

        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException());

        if (refreshToken.equals(redisRefreshToken.getRefreshToken())) {

            String accessToken = tokenProvider.generateAccessToken(email);

            redisRefreshToken.setAccessToken(accessToken);
            refreshTokenRedisRepository.save(redisRefreshToken);

            return LoginResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        }

        throw new UnAuthException();
    }

    @Transactional
    public Member findByMemberEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow();
    }

}
