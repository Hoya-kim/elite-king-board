package board.api.common;

import static java.util.Objects.isNull;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageUtils {

    private static final int MIN_PAGE_VALUE = 1;

    public int getPage(Integer page) {
        return isNull(page) ? 0 : page - 1;
    }

    public Pageable getPageable(
        Integer page,
        int pageSize,
        Sort.Direction direction,
        String... properties)
    {
        return PageRequest.of(getPage(page), pageSize, Sort.by(direction, properties));
    }

    public PageResponseData getPageResponseData(Page<?> pageList, int scaleSize) {
        PageResponseData pageResponseData = new PageResponseData();

        pageResponseData.setPagingInfo(pageList.getNumber(), pageList.getTotalPages(), scaleSize);

        return pageResponseData;
    }

    public boolean isInvalidPageValue(Integer page) {
        return !isNull(page) && page < MIN_PAGE_VALUE;
    }

    public PagingResponseDto getCommonPagingResponseDto(
        Page<?> pageList,
        List<?> responseDto,
        int scaleSize)
    {
        return PagingResponseDto.builder()
            .responseDto(responseDto)
            .pageResponseData(getPageResponseData(pageList, scaleSize))
            .build();
    }
}
