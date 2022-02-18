package com.board.server.modules.member.mapper;

import com.board.server.modules.member.Account;
import com.board.server.modules.member.dto.SignUpResponseDto;

public class AccountMapper {

    public SignUpResponseDto toResponseDto(Account account) {
        SignUpResponseDto responseDto = new SignUpResponseDto();
        responseDto.setNickname(account.getNickname());
        responseDto.setEmail(account.getEmail());
        responseDto.setRole(account.getRole());
        responseDto.setJoinedAt(account.getJoinedAt());

        return responseDto;
    }
}
