package com.board.server.modules.member.mapper;

import com.board.server.modules.member.Member;
import com.board.server.modules.member.dto.SignUpResponseDto;

public class MemberMapper {

    public SignUpResponseDto toResponseDto(Member member) {
        SignUpResponseDto responseDto = new SignUpResponseDto();
        responseDto.setNickname(member.getNickname());
        responseDto.setEmail(member.getEmail());
        responseDto.setRole(member.getRole());
        responseDto.setJoinedAt(member.getJoinedAt());

        return responseDto;
    }
}
