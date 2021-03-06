package board.api.modules.articles;

import board.api.modules.articles.dto.ArticleDto;
import board.api.modules.articles.dto.CreateArticleRequest;
import board.api.modules.articles.dto.CreateArticleResponse;
import board.api.modules.articles.dto.GetAricleResponse;
import board.api.modules.articles.dto.Result;
import board.api.modules.articles.dto.UpdateArticleRequest;
import board.api.modules.articles.dto.UpdateArticleResponse;
import board.api.modules.articles.model.Article;
import board.api.modules.articles.service.ArticleService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/articles")
    public Result getArticle() {

        List<Article> articles = articleService.findArticles();
        articles.stream().map(
                m -> new ArticleDto(m.getId(), m.getTitle(), m.getContent(), m.getViewCount(),
                    m.getLikeCount(), m.getCreatedBy(), m.getModifiedBy()))
            .collect(Collectors.toList());

        return new Result(articles);
    }

    @GetMapping("/articles/{id}")
    public Map<String, Object> getArticleNumber(@PathVariable("id") Long id) {

        Map<String, Object> response = new HashMap<>();
        Article article = articleService.findOne(id);
        articleService.updateLikeViewCount(id);

        try {
            response.put("result",
                new GetAricleResponse(article.getId(), article.getTitle(), article.getContent(),
                    article.getViewCount(), article.getLikeCount(),
                    article.getCreatedAt(), article.getCreatedBy(), article.getModifiedAt(),
                    article.getModifiedBy()));
        } catch (Exception e) {
            response.put("result", "FAIL");
            response.put("reason", "?????? ????????? ????????? ??????????????????.");
        }

        return response;
    }


    @DeleteMapping("/articles/{id}")
    public Map<String, Object> deleteArticle(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            articleService.delete(id);
            response.put("result", "SUCCESS");
        } catch (Exception e) {
            response.put("result", "FAIL");
            response.put("reason", "???????????? ?????? ????????? ????????????. ????????? ID??? ??????????????????.");
        }
        return response;
    }


    @PostMapping("/articles")
    public Map<String, Object> saveArticle(@RequestBody @Valid CreateArticleRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long id = articleService.join(request);
            response.put("result", new CreateArticleResponse(id));
        } catch (Exception e) {
            response.put("result", "FAIL");
            response.put("reason", "???????????? ????????? ??? ????????????.");
        }
        return response;
    }

    @PutMapping("/articles/{id}")
    public Map<String, Object> updateArticle(
        @PathVariable("id") Long id,
        @RequestBody @Valid UpdateArticleRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            articleService.update(id, request);
            Article article = articleService.findOne(id);
            response.put("result", new UpdateArticleResponse(article.getId(), article.getTitle(),
                article.getContent(),
                article.getCreatedAt(), article.getCreatedBy(), article.getModifiedAt()
                ));
        } catch (Exception e) {
            response.put("result", "FAIL");
            response.put("reason", "?????? ???????????? ????????? ??? ????????????.");
        }
        return response;
    }

}
