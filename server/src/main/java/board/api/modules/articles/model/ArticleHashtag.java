package board.api.modules.articles.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "article_hashtag")
public class ArticleHashtag {

    @Id
    @GeneratedValue
    @Column(name = "article_hashtag_id", columnDefinition = "bigint not null comment 'ID'")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", columnDefinition = "bigint not null comment '게시글아이디'")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "hashtag_id", columnDefinition = "bigint not null comment '해시태그아이디'")
    private HashTag hashTag;
}
