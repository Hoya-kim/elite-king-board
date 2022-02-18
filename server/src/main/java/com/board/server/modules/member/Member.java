package com.board.server.modules.member;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class Member {

    @Getter
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Getter @Setter private String nickname;
    
    @Column(nullable = false)
    @Setter private String password;
    
    @Column(nullable = false, unique = true)
    @Getter @Setter private String email;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private Role role;

    @Column(nullable = false)
    @Getter private LocalDateTime joinedAt;

    public Member(String nickname, String password, String email, Role role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
