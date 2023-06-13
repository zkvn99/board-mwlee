package idusw.springboot.boradmwlee.repository;

import idusw.springboot.boradmwlee.entity.BoardEntity;
import idusw.springboot.boradmwlee.entity.BoardLikeEntity;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, Long>{
    BoardLikeEntity findBySeqAndBno(MemberEntity seq, BoardEntity bno);
}
