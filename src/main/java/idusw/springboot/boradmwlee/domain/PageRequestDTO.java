package idusw.springboot.boradmwlee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;


@Builder
@AllArgsConstructor
@Data // @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
public class PageRequestDTO {
    private int page; // 요청하는 페이지
    private int size; // 페이지당 게시물 수

    public PageRequestDTO() { // == @NoArgsConstrutor => 값을 설정하기 위해 사용 안함
        this.page = 1;
        this.size = 10;
    }
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page -1, size, sort);
    }
}
