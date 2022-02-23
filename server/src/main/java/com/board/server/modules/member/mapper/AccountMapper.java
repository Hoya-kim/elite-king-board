package com.board.server.modules.member.mapper;

import com.board.server.modules.member.Account;
import com.board.server.modules.member.dto.SignUpResponseDto;

public class AccountMapper {

    public SignUpResponseDto toResponseDto(Account account) {
        return new SignUpResponseDto(account);
    }
}
