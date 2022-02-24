package board.api.modules.article_comments;

import board.api.modules.article_comments.dto.ArticleCommentRequestDto;
import board.api.modules.article_comments.dto.ArticleCommentResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/articles/{articleId}/article-comments")
    public ResponseEntity<ArticleCommentResponseDto> addComment(
        @PathVariable Long articleId,
        @RequestBody @Valid ArticleCommentRequestDto.Post articleCommentRequestDto) {

        return ResponseEntity.ok(
            articleCommentService.addComment(articleId, articleCommentRequestDto));
    }

}
