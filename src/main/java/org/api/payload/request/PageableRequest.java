package org.api.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.api.enumeration.SortEnum;
import org.api.payload.PageCommon;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableRequest extends PageCommon implements Serializable {
    private static final long serialVersionUID = 1L;

    private Pageable pageable;
    private Sort sort;

    public Sort getSort() {
        if(getSorts().equals(SortEnum.ASC.getText()))
            return Sort.by(Direction.ASC, getField());
        return Sort.by(Direction.DESC, getField());
    }

    public Pageable getPageable() {
        return PageRequest.of(getPage() - 1, getSize(), getSort());
    }

}
