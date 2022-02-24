package board.api.modules.article_comments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    @Query(value = "select a from ArticleComment a join a.article where a.article.id = :id")
    Page<ArticleComment> findAllByArticleId(@Param("id") Long articleId, Pageable createdAt);

}
