package idusw.springboot.boradmwlee.service;

import idusw.springboot.boradmwlee.domain.Board;
import idusw.springboot.boradmwlee.domain.PageRequestDTO;
import idusw.springboot.boradmwlee.domain.PageResultDTO;
import idusw.springboot.boradmwlee.entity.BoardEntity;
import idusw.springboot.boradmwlee.entity.MemberEntity;


public interface BoardService {
    int registerBoard(Board board);

    Board findBoardById(Board board); // 게시물의 ID (유일한 식별자) - 즉, ano로 조회

    PageResultDTO<Board, Object[]> findBoardAll(PageRequestDTO pageRequestDTO); // 게시물 목록 출력 (페이지 처리, 정렬, 필터 ==? 검색)

    int updateBoard(Board board); // 게시물 정보

    int deleteBoard(Board board); // 게시물의 ID 값만

    int boardLike(Board board);

    default BoardEntity dtoToEntity(Board dto) { // dto객체를 entity 객체로 변환 : service -> repository
        MemberEntity member = MemberEntity.builder()
                .seq(dto.getWriterSeq())
                .build();
        BoardEntity entity = BoardEntity.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .boardLike(dto.getBoardLike())
                .build();
        return entity;
    }

    default Board entityToDto(BoardEntity entity, MemberEntity memberEntity, Long replyCount) { // entity 객체를 dto 객체로 변환 : service -> controllerb
        Board dto = Board.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writerSeq(memberEntity.getSeq())
                .writerEmail(memberEntity.getEmail())
                .writerName(memberEntity.getName())
                .replyCount(replyCount)
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .boardLike(entity.getBoardLike())
                .build();
        return dto;
    }
}
