package idusw.springboot.boradmwlee.repository;

import idusw.springboot.boradmwlee.entity.BoardEntity;
import idusw.springboot.boradmwlee.entity.BoardLikeEntity;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, Long>{
    BoardLikeEntity findBySeqAndBno(MemberEntity seq, BoardEntity bno);

    @Modifying
    @Query("delete from BoardLikeEntity r where r.bno = :board")
    void deleteByBno(BoardEntity board);
}
