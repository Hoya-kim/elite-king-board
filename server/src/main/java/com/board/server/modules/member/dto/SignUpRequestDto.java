package com.board.server.modules.member.dto;

import com.board.server.modules.member.Member;
import com.board.server.modules.member.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class SignUpRequestDto {

    @NotBlank
    @Pattern(regexp = "([가-힣a-zA-Z0-9_]{3,15}$)")
    private String nickname;

    @Length(min = 8, max = 30)
    @NotBlank
    private String password;

    @Email
    @Pattern(regexp = ".+@11stcorp.com$")
    @NotBlank
    private String email;

    public Member toEntity() {
        return new Member(nickname, password, email, Role.USER);
    }
}
