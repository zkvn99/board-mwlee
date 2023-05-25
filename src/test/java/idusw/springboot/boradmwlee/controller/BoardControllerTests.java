package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Board;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import idusw.springboot.boradmwlee.repository.BoardRepository;
import idusw.springboot.boradmwlee.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardControllerTests {

    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;

    @Test
    void registerBoard() {
        Board board = Board.builder()
                .bno(1L)
                .title("title")
                .content("content")
                .writerSeq(1L)
                .writerEmail("minwook@induk.ac.kr")
                .writerName("m")
                .build();
        if(boardService.registerBoard(board) > 0)
            System.out.println("등록 성공");
        else
            System.out.println("등록 실패");
    }
}
