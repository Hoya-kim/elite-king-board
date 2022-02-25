package board.api.modules.articles.service;

import board.api.modules.account.Account;
import board.api.modules.account.AccountRepository;
import board.api.modules.articles.dto.CreateArticleRequest;
import board.api.modules.articles.dto.UpdateArticleRequest;
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

    private final String INVALID_ARTICLE_MESSAGE = "존재하지 않는 게시글입니다.";

    private final ArticleRepository articleRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void update(Long id, UpdateArticleRequest request) {
        Article article = findOne(id);
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
    }

    public Article findOne(Long id) {
        return articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ARTICLE_MESSAGE));
    }

    @Transactional
    public Long join(CreateArticleRequest request) {

        Account account = accountRepository.findById(request.getAccountId()).get();
        Article article = new Article(request, account);
        articleRepository.save(article);
        return article.getId();
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
