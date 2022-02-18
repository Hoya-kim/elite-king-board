package com.board.server.modules.member;

import com.board.server.modules.member.dto.SignUpRequestDto;
import com.board.server.modules.member.dto.SignUpResponseDto;
import com.board.server.modules.member.mapper.MemberMapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping("/members/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signupRequestDto) {
        Member newMember = memberService.signUp(signupRequestDto);
        SignUpResponseDto responseDto = memberMapper.toResponseDto(newMember);

        return ResponseEntity
                .created(URI.create("/members/" + newMember.getId()))
                .body(responseDto);
    }
}
