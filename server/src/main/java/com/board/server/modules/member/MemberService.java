package com.board.server.modules.member;

import com.board.server.modules.member.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signUp(SignUpRequestDto signUpRequestDto) {
        signUpRequestDto.setPassword(encode(signUpRequestDto.getPassword()));
        return memberRepository.save(signUpRequestDto.toEntity());
    }

    private String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
