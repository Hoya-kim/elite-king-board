package board.api.common;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PagingResponseDto {

    private List<?> responseDto;
    private PageResponseData pageResponseData;

    @Builder
    public PagingResponseDto(List<?> responseDto, PageResponseData pageResponseData) {
        this.responseDto = responseDto;
        this.pageResponseData = pageResponseData;
    }
}
