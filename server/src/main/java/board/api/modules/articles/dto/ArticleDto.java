package board.api.modules.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleDto {

    private Long id;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private String createdBy;
    private String modifiedBy;
}
