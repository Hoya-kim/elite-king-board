package board.api.modules.articles.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateArticleResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private String created_by;
    private LocalDateTime modified_at;
}
