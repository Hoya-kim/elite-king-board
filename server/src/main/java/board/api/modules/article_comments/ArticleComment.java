package board.api.modules.article_comments;

import board.api.modules.article_comments.dto.ArticleCommentRequestDto;
import board.api.modules.articles.model.Article;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
@Table(name = "article_comment")
public class ArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @Setter
    private Article article;

    @Setter
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Setter
    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Setter
    @LastModifiedBy
    private String modifiedBy;

    @Builder
    public ArticleComment(Article article, String content, String createdBy) {
        this.article = article;
        this.content = content;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    public void update(ArticleCommentRequestDto.Put articleCommentRequestDto) {
        this.content = articleCommentRequestDto.getContent();
        this.modifiedBy = articleCommentRequestDto.getModifiedBy();
    }
}
