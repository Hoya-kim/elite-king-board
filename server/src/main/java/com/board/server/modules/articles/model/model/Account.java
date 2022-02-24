package com.board.server.modules.articles.model.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @OneToMany(mappedBy = "account")
    private List<Article> article = new ArrayList<>();

    private String nickname;

    private String password;

    private String email;

    private LocalDateTime joined_at;

    @Enumerated(EnumType.STRING)
    private Role role;
}
