package board.api.modules.account;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "email"})
@Entity
public class Account {

    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Column(nullable = false)
    private String nickname;

    @Getter
    @Column(nullable = false)
    private String password;

    @Getter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Account(String nickname, String password, String email, Role role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
