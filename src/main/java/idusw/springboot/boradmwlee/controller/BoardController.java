package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Board;
import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.domain.PageRequestDTO;
import idusw.springboot.boradmwlee.domain.PageResultDTO;
import idusw.springboot.boradmwlee.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/boards")
public class BoardController {
    HttpSession session = null;

    private final BoardService boardService; // BoardController에서 사용할 BoardService 객체를 참조하는 변수
    public BoardController(BoardService boardService) {
// Spring Framework 가 BoardService 객체를 주입, boardService(주입될 객체의 참조변수)
        this.boardService = boardService;
    }

    @GetMapping("/reg-form")
    public String getRegForm(PageRequestDTO pageRequestDTO, Model model, HttpServletRequest request) {
        session = request.getSession();
        Member member = (Member) session.getAttribute("mb");
        if (member != null) {
            model.addAttribute("board", Board.builder().build());
            return "/boards/reg-form";
        } else
            return "redirect:/members/login-form"; // 로그인이 안된 상태인 경우
    }

    @PostMapping("")
    public String postBoard(@ModelAttribute("board") Board dto, Model model, HttpServletRequest request) {
        session = request.getSession();
        Member member = (Member) session.getAttribute("mb");

        if (member != null) {
// form에서 hidden 전송하는 방식으로 변경
            dto.setWriterSeq(member.getSeq());
            dto.setWriterEmail(member.getEmail());
            dto.setWriterName(member.getName());

            Long bno = Long.valueOf(boardService.registerBoard(dto));

            return "redirect:/boards"; // 등록 후 목록 보기, redirection, get method
        } else
            return "redirect:/members/login"; // 로그인이 안된 상태인 경우
    }

    @GetMapping("")
    public String getBoards(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model) { // 중간 본 수정
        //PageRequestDTO pageRequestDTO1 = PageRequestDTO.builder().build();
        //PageResultDTO<Board, Object[]> pageResultDTO = boardService.findBoardAll(pageRequestDTO);
        if(pageRequestDTO == null)
            model.addAttribute("pageRequestDTO", PageRequestDTO.builder().build());
        else
            model.addAttribute("list", boardService.findBoardAll(pageRequestDTO));
        //model.addAttribute("list", pageResultDTO);
        return "/boards/list";
    }

    @GetMapping("/{bno}")
    public String getBoardByBno(@PathVariable("bno") Long bno, Model model) {
        // Long bno 값을 사용하는 방식을 Board 객체에 bno를 설정하여 사용하는 방식으로 변경
        Board board = boardService.findBoardById(Board.builder().bno(bno).build());
        boardService.updateBoard(board);
        model.addAttribute("board", board);
        System.out.println(board);
        return "/boards/detail";
    }

    @GetMapping("/{bno}/up-form")
    public String getUpForm(@PathVariable("bno") Long bno, Model model, HttpServletRequest request) {
        /*Board board = boardService.findBoardById(Board.builder().bno(bno).build());
        model.addAttribute("board", board);
        return "/boards/up-form";*/
        session = request.getSession();
        Member member = (Member) session.getAttribute("mb");

        if (member == null) {
            // 로그인이 되어있지 않은 경우 처리
            return "redirect:/members/login";
        }

        Board board = boardService.findBoardById(Board.builder().bno(bno).build());

        if (member.getSeq().equals(board.getWriterSeq())) {
            model.addAttribute("board", Board.builder().build());
            return "/boards/up-form";
        } else {
            return "redirect:/boards";
        } // 작성자가 일치하지 않을 경우
    }

    @PutMapping("/{bno}/update")
    public String putBoard(@ModelAttribute("board") Board board, Model model) {

        boardService.updateBoard(board);
        model.addAttribute(boardService.findBoardById(board));
        return "redirect:/boards/" + board.getBno();
    }

    @GetMapping("/{bno}/del-form")
    public String getDelForm(@PathVariable("bno") Long bno, Model model) {
        Board board = boardService.findBoardById(Board.builder().bno(bno).build());
        model.addAttribute("board", board);
        return "/boards/del-form";
    }

    @DeleteMapping("/{bno}/delete")
    public String deleteBoard(@ModelAttribute("board") Board board, Model model) {
        boardService.deleteBoard(board);
        model.addAttribute(board);
        return "redirect:/boards";
    }


    @PostMapping("/")
    public String createMember(@ModelAttribute("board") Board board, Model model) { // 등록 처리 -> service -> repository -> service -> controller
        if (boardService.registerBoard(board) > 0) // 정상적으로 레코드의 변화가 발생하는 경우 영향받는 레코드 수를 반환
            return "redirect:/boards";
        else
            return "/errors/404"; // 게시물 등록 예외 처리
    }
}
