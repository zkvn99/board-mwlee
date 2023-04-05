package idusw.springboot.boradthymleaf.repository;

import idusw.springboot.boradthymleaf.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {

}
