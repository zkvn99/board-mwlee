package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Board;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import idusw.springboot.boradmwlee.repository.BoardRepository;
import idusw.springboot.boradmwlee.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
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
        IntStream.rangeClosed(1, 100)
                .forEach(i -> {
                    Board board = Board.builder()
                            .bno((long) 1L)
                            .title("title" + i)
                            .content("content" + i)
                            .writerSeq(1L)
                            .writerEmail("root201912012@induk.ac.kr")
                            .writerName("이민욱")
                            .boardLike(0L)
                            .build();
                    if (boardService.registerBoard(board) > 0) {
                        System.out.println("등록 성공: " + i);
                    } else {
                        System.out.println("등록 실패: " + i);
                    }
                });
    }
}
