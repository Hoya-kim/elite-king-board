package board.api.modules.article_comments;

import board.api.modules.article_comments.dto.ArticleCommentRequestDto;
import board.api.modules.article_comments.dto.ArticleCommentResponseDto;
import board.api.modules.articles.model.Article;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    private final String INVALID_ARTICLE_MESSAGE = "존재하지 않는 게시글입니다.";

    @Transactional
    public ArticleCommentResponseDto addComment(
        Long articleId,
        ArticleCommentRequestDto.Post articleCommentRequestDto
    ) {
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ARTICLE_MESSAGE));

        ArticleComment articleComment = articleCommentRepository.save(
            articleCommentRequestDto.toEntity(article));

        return modelMapper.map(articleComment, ArticleCommentResponseDto.class);
    }

}
