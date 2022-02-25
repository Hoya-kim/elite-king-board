package board.api.modules.article_comments;

import board.api.common.PagingResponseDto;
import board.api.modules.article_comments.dto.ArticleCommentRequestDto;
import board.api.modules.article_comments.dto.ArticleCommentResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/articles")
@RequiredArgsConstructor
@RestController
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/{articleId}/article-comments")
    public ResponseEntity<ArticleCommentResponseDto> addComment(
        @PathVariable Long articleId,
        @RequestBody @Valid ArticleCommentRequestDto.Post articleCommentRequestDto) {

        return ResponseEntity.ok(
            articleCommentService.addComment(articleId, articleCommentRequestDto));
    }

    @GetMapping("/{articleId}/article-comments")
    public ResponseEntity<PagingResponseDto> getComments(
        @PathVariable Long articleId,
        @RequestParam(value = "page", required = false) Integer page) {
        return ResponseEntity.ok(articleCommentService.getComments(articleId, page));
    }

    @PutMapping("/{articleId}/article-comments/{articleCommentId}")
    public ResponseEntity<ArticleCommentResponseDto> updateComment(
        @PathVariable("articleId") Long articleId,
        @PathVariable("articleCommentId") Long articleCommentId,
        @RequestBody @Valid ArticleCommentRequestDto.Put articleCommentRequestDto) {

        return ResponseEntity.ok(
            articleCommentService.updateComment(
                articleId,
                articleCommentId,
                articleCommentRequestDto));
    }

    @DeleteMapping("/{articleId}/article-comments/{articleCommentId}")
    public ResponseEntity<ArticleCommentResponseDto> deleteComment(
        @PathVariable("articleId") Long articleId,
        @PathVariable("articleCommentId") Long articleCommentId) {

        return ResponseEntity.ok(
            articleCommentService.deleteComment(articleId, articleCommentId));
    }
}
