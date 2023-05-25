package idusw.springboot.boradmwlee.service;

import idusw.springboot.boradmwlee.domain.Board;
import idusw.springboot.boradmwlee.entity.BoardEntity;
import idusw.springboot.boradmwlee.entity.MemberEntity;

import java.util.List;

public interface BoardService {
    int registerBoard(Board board);

    Board findBoardById(Board board); // 게시물의 ID (유일한 식별자) - 즉, ano로 조회

    List<Board> findBoardAll(); // 게시물 목록 출력 (페이지 처리, 정렬, 필터 ==? 검색)

    int updateBoard(Board board); // 게시물 정보

    int deleteBoard(Board board); // 게시물의 ID 값만

    default BoardEntity dtoToEntity(Board dto) { // dto객체를 entity 객체로 변환 : service -> repository
        MemberEntity member = MemberEntity.builder()
                .seq(dto.getWriterSeq())
                .build();
        BoardEntity entity = BoardEntity.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return entity;
    }

    default Board entityToDto(BoardEntity entity, MemberEntity memberEntity) { // entity 객체를 dto 객체로 변환 : service -> controller
        Board dto = Board.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writerSeq(memberEntity.getSeq())
                .writerEmail(memberEntity.getEmail())
                .writerName(memberEntity.getName())
                .build();
        return dto;
    }
}
