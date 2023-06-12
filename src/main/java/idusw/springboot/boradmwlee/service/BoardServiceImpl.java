package idusw.springboot.boradmwlee.service;

import idusw.springboot.boradmwlee.domain.Board;
import idusw.springboot.boradmwlee.domain.PageRequestDTO;
import idusw.springboot.boradmwlee.domain.PageResultDTO;
import idusw.springboot.boradmwlee.entity.BoardEntity;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import idusw.springboot.boradmwlee.repository.BoardRepository;
import idusw.springboot.boradmwlee.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository; // RequiredArgConstructor 사용 시 final
    private final ReplyRepository replyRepository;

    /* RequiredArgConstructor가 해주는 코드
    public BoardServiceImpl(BoardRepository boardRepository, ReplyRepository replyRepository){
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }
    */

    @Override
    public int registerBoard(Board dto) {
            // DTO -> Entity : Repository에서 처리하기 위해

            BoardEntity entity = dtoToEntity(dto);

            if(boardRepository.save(entity) != null) // 저장 성공
                return 1;
            else
                return 0;
    }

    @Override
    public Board findBoardById(Board board) {
        Object[] entities = (Object[]) boardRepository.getBoardByBno(board.getBno());
        return entityToDto((BoardEntity) entities[0], (MemberEntity) entities[1], (Long) entities[2]);
    }

    @Override
    public PageResultDTO<Board, Object[]> findBoardAll(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Function<Object[], Board> fn = (entity -> entityToDto((BoardEntity) entity[0],
                (MemberEntity) entity[1], (Long) entity[2]));
        return new PageResultDTO<>(result, fn, 5);
    }


    @Override
    public int updateBoard(Board board) {
        BoardEntity entity = BoardEntity.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(boardRepository.findById(board.getBno()).get().getWriter())
                .build();
        if(boardRepository.save(entity) != null) // 저장 성공
            return 1;
        else
            return 0;
    }

    @Transactional
    @Override
    public int deleteBoard(Board board) {
        replyRepository.deleteByBno(board.getBno()); // 댓글 삭제
        boardRepository.deleteById(board.getBno()); // 게시물 삭제
        return 0;
    }

    @Override
    public int boardLike(Board board) {
        return 0;
    }
}
