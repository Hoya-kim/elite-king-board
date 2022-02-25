package board.api.modules.articles.repository;

import board.api.modules.articles.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {



}
