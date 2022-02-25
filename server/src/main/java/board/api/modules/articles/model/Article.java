package board.api.modules.articles.model;

import board.api.modules.account.Account;
import board.api.modules.article_comments.ArticleComment;
import board.api.modules.articles.dto.CreateArticleRequest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "article")
@NoArgsConstructor
public class Article extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private Integer viewCount;

    private Integer likeCount;


    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "article")
    private List<ArticleComment> articleComments = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<ArticleHashtag> articleHashTags = new ArrayList<>();

    public Article(CreateArticleRequest request, Account account) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.viewCount = request.getViewCount();
        this.likeCount = request.getLikeCount();
        this.createdBy = request.getNickname();
        this.modifiedBy = createdBy;
        this.account = account;
    }
}
