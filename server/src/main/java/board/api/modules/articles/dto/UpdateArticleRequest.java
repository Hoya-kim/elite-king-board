package board.api.modules.articles.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UpdateArticleRequest {

    private Long id;
    private String title;
    private String content;
    private Integer view_count;
    private Integer like_count;
    private LocalDateTime created_at;
    private String created_by;
    private LocalDateTime modified_at;
    private String modified_by;
}
