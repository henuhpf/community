package code.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrePage;
    private boolean showFirstPage;
    private boolean showNextPage;
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer>  pages = new ArrayList<>();

    /**
     * 设置分页条显示
     * @param totalCount 总数
     * @param page 当前页
     * @param size 每页大小
     */
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        totalPage = (totalCount + size - 1) / size;
        if(page <= 0){
            page = 1;
        }
        if(page >= totalPage) {
            page = totalPage;
        }
        this.page = page;
        int tmp = page - 2;
        if(page + 2 > totalPage) {
            tmp = totalPage - 5 + 1;
        }
        if(tmp <= 0){
            tmp = 1;
        }

        while(pages.size() < 5 && tmp <= totalPage){
            pages.add(tmp);
            tmp++;
        }
        showPrePage = page == 1 ? false : true;
        showNextPage = page == totalPage ? false : true;
        showFirstPage = !pages.contains(1);
        showEndPage = !pages.contains(totalPage);
    }
}
