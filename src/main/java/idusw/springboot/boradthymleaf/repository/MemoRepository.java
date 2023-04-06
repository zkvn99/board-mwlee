package idusw.springboot.boradthymleaf.repository;

import idusw.springboot.boradthymleaf.entity.MemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<MemoEntity, Long> {

}
