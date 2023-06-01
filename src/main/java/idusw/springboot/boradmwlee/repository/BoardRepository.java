package idusw.springboot.boradmwlee.repository;

import idusw.springboot.boradmwlee.entity.BoardEntity;
import idusw.springboot.boradmwlee.repository.search.SearchBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long>, SearchBoardRepository {
}
