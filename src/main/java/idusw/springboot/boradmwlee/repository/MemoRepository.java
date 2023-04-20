package idusw.springboot.boradmwlee.repository;

import idusw.springboot.boradmwlee.entity.MemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<MemoEntity, Long> {

}
