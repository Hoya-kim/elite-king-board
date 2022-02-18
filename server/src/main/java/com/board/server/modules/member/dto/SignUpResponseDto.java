package com.board.server.modules.member.dto;

import com.board.server.modules.member.Role;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SignUpResponseDto {

    private String nickname;

    private String email;

    private LocalDateTime joinedAt;

    private Role role;
}
