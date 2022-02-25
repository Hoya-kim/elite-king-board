package board.api.modules.articles.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UpdateArticleRequest {

    private Long account_id;
    private String title;
    private String content;
}
