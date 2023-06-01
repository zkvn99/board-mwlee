package idusw.springboot.boradmwlee.repository.search;
import idusw.springboot.boradmwlee.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    BoardEntity searchBoard(); // 중간 단계 코드
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}