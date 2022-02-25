package board.api.common;

import lombok.Getter;

@Getter
public class PageResponseData {

    private int page = 1;
    private int scaleStartPage = 1;
    private int scaleEndPage = 1;
    private int totalPage = 1;
    private Integer prevPage;
    private Integer nextPage;

    public void setPagingInfo(int pageNumber, int totalPage, int scaleSize) {
        this.page = pageNumber + 1;

        int nowScale = (page - 1) / scaleSize;

        int startPage = (nowScale - 1) * scaleSize + 1;
        int endPage = startPage + scaleSize - 1;

        endPage = Math.min(endPage, totalPage);

        this.scaleStartPage = startPage;
        this.scaleEndPage = endPage;
        this.prevPage = (startPage - 1) > 1 ? (startPage - 1) : null;
        this.nextPage = (endPage + 1) <= totalPage ? (endPage + 1) : null;
        this.totalPage = totalPage;
    }
}
