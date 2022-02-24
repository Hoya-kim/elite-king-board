package com.board.server.modules.articles.model.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "hashtag")
public class HashTag {

    @Id
    @GeneratedValue
    @Column(name = "hashtag_id", columnDefinition = "bigint not null comment 'ID'")
    private Long id;

    @Column(columnDefinition = "varchar(255) not null comment '해시태그'")
    private String hashTag;

    @OneToMany(mappedBy = "hashTag")
    private List<ArticleHashtag> articleHashTags = new ArrayList<>();
}
