package org.api.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.api.payload.PageCommon;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@AllArgsConstructor
@JsonSerialize
public class PageResponse<T> extends PageCommon implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Page<T> resultPage;
    private Integer totalPage;
    private Integer startPage;
    private Integer endPage;
    private List<Integer> pageNumbers;
    private List<T> results;

    public Integer getTotalPage() {
        return resultPage.getTotalPages();
    }

    public Integer getStartPage() {
        return Math.max(1, getPage() - 2);
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public Integer getEndPage() {
        return Math.min(getPage() + 2, getTotalPage());
    }

    public List<Integer> getPageNumbers() {
        int startPages = getStartPage();
        int endPages = getEndPage();
        if (getTotalPage() > 5) {
            if (getEndPage() == getTotalPage()){
                startPages = endPages - 5;
                setStartPage(startPages);
            }
            else if (getStartPage() == 1){
                endPages = startPages + 5;
                setEndPage(endPages);
            }
        }
        return IntStream.rangeClosed(startPages, endPages).boxed().collect(Collectors.toList());
    }

    public List<T> getResults() {
        return resultPage.getContent();
    }

    public PageResponse() {
    }
}
