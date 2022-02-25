package board.api.modules.articles.dto;

import lombok.Data;

@Data
public class CreateArticleRequest {

    private Long accountId;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
}
