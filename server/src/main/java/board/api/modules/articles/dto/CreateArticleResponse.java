package board.api.modules.articles.dto;

import lombok.Data;

@Data
public class CreateArticleResponse {


    private Long id;

    public CreateArticleResponse(Long id) {
        this.id = id;
    }

}
