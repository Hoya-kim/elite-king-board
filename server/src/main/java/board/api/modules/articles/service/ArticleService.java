package board.api.modules.articles.service;

import board.api.modules.account.Account;
import board.api.modules.account.AccountRepository;
import board.api.modules.articles.dto.CreateArticleRequest;
import board.api.modules.articles.model.Article;
import board.api.modules.articles.repository.ArticleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void update(Long id, String title) {
        Article article = articleRepository.findOne(id);
        article.setTitle(title);
    }

    public Article findOne(Long id) {
        return articleRepository.findOne(id);
    }

    @Transactional
    public Long join(CreateArticleRequest request) {

        Account account = accountRepository.findById(request.getAccountId()).get();
        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setViewCount(request.getViewCount());
        article.setLikeCount(request.getLikeCount());
        article.setCreatedBy(request.getCreatedBy());
        article.setModifiedBy(request.getModifiedBy());
        article.setAccount(account);

        articleRepository.save(article);
        return article.getId();
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        Article user = articleRepository.findOne(id);
        articleRepository.delete(user);
    }
}
