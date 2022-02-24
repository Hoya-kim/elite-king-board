package board.api.modules.articles.repository;

import board.api.modules.articles.model.Article;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    private final EntityManager em;

    public List<Article> findAll() {
        return em.createQuery("select a from Article a", Article.class)
            .getResultList();
    }

    public void save(Article article) {
        if (article.getId() == null) {
            em.persist(article);
        }
    }

    public Article findOne(Long id) {
        return em.find(Article.class, id);
    }

    public void delete(Article article) {
        em.remove(article);
    }


}
