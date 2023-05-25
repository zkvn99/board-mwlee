package idusw.springboot.boradmwlee.service;

import idusw.springboot.boradmwlee.domain.Board;
import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.entity.BoardEntity;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import idusw.springboot.boradmwlee.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
    private BoardRepository boardRepository;
    public BoardServiceImpl(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }


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
        return null;
    }

    @Override
    public List<Board> findBoardAll() {
        return null;
    }

    @Override
    public int updateBoard(Board board) {
        return 0;
    }

    @Override
    public int deleteBoard(Board board) {
        return 0;
    }
}
