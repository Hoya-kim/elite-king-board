package com.board.server.modules.member;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "email"})
@Entity
public class Account {

    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Getter
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    @Getter
    private String email;

    @Enumerated(EnumType.STRING)
    @Getter
    private Role role;

    @DateTimeFormat(iso = ISO.DATE_TIME)
//    @Column(nullable = false)
    @Getter
    private LocalDateTime joinedAt;

    public Account(String nickname, String password, String email, Role role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
