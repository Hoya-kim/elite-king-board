package com.board.server.modules.member;

import com.board.server.modules.member.dto.SignUpRequestDto;
import com.board.server.modules.member.dto.SignUpResponseDto;
import com.board.server.modules.member.mapper.AccountMapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("/members/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signupRequestDto) {
        Account newAccount = accountService.signUp(signupRequestDto);
        SignUpResponseDto responseDto = accountMapper.toResponseDto(newAccount);

        return ResponseEntity
                .created(URI.create("/members/" + newAccount.getId()))
                .body(responseDto);
    }
}
