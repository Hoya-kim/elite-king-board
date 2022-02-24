package com.board.server.modules.articles.model.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    private String title;

    private String content;

    private Integer viewCount;

    private Integer likeCount;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime modifiedAt;
    private String modifiedBy;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "article")
    private List<ArticleComment> articleComments = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<ArticleHashtag> articleHashTags = new ArrayList<>();

}
